package org.lenndi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.ladutsko.isbn.ISBNException;
import org.lenndi.umtapo.dao.ItemDao;
import org.lenndi.umtapo.dto.ItemDto;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.mapper.ItemMapper;
import org.lenndi.umtapo.service.generic.AbstractGenericService;
import org.lenndi.umtapo.service.specific.ItemService;
import org.lenndi.umtapo.solr.document.bean.record.Identifier;
import org.lenndi.umtapo.solr.document.bean.record.Record;
import org.lenndi.umtapo.solr.exception.InvalidRecordException;
import org.lenndi.umtapo.solr.service.SolrItemService;
import org.lenndi.umtapo.solr.service.SolrRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Item service implementation.
 */
@Service
public class ItemServiceImpl extends AbstractGenericService<Item, Integer> implements ItemService {

    private final ItemMapper itemMapper;
    private final ItemDao itemDao;
    private final SolrRecordService solrRecordService;
    private final SolrItemService solrItemService;

    /**
     * Instantiates a new Item service.
     *
     * @param itemMapper        the item mapper
     * @param itemDao           the item dao
     * @param solrRecordService the solr record service
     * @param solrItemService   the solr item service
     */
    @Autowired
    public ItemServiceImpl(
            ItemMapper itemMapper,
            ItemDao itemDao,
            SolrRecordService solrRecordService,
            SolrItemService solrItemService
    ) {
        this.itemMapper = itemMapper;
        this.itemDao = itemDao;
        this.solrRecordService = solrRecordService;
        this.solrItemService = solrItemService;
    }

    @Override
    @Transactional
    public Item save(Item item) {
        if (item.getRecord() == null) {
            item = this.linkRecord(item);
        }

        item = super.save(item);
        this.solrItemService.save(item);

        return item;
    }

    @Override
    @Transactional
    public Item saveWithRecord(Item item) throws InvalidRecordException {
        Record record = null;

        if (item.getRecord() != null) {
            Identifier identifier = item.getRecord().getIdentifier();
            String serial = identifier.getSerialNumber();
            String serialType = identifier.getSerialType();
            List<Record> records = this.solrRecordService.searchBySerialNumber(serial, serialType);

            if (records.isEmpty()) {
                record = this.solrRecordService.save(item.getRecord());
            } else {
                record = records.get(0);
            }

            item.setRecordId(record.getId());
        }

        if (item.getInternalId() == null && item.getExternalLibrary() == null) {
            Integer previousInternalId = this.itemDao.findTopInternalId();
            item.setInternalId(previousInternalId + 1);
        }

        Item savedItem = this.save(item);
        if (record != null) {
            record.addItem(item.getId().toString());
            this.solrRecordService.save(record);
        }

        return savedItem;
    }

    @Override
    public ItemDto saveDto(ItemDto itemDto) throws InvalidRecordException {
        Item item = this.itemMapper.mapItemDtoToItem(itemDto);

        if (item.getExternalLibrary() != null && item.getExternalLibrary().getId() == null) {
            item.setExternalLibrary(null);
        }
        item = this.saveWithRecord(item);

        return this.itemMapper.mapItemToItemDto(item);
    }

    @Override
    public ItemDto updateDto(ItemDto itemDto) {
        Item updatedItem = this.itemMapper.mapItemDtoToItem(itemDto);
        Item item = this.findOne(itemDto.getId());

        item.setCondition(updatedItem.getCondition());
        item.setCurrency(updatedItem.getCurrency());
        item.setLoanable(updatedItem.getLoanable());
        item.setType(updatedItem.getType());
        item.setShelfmark(updatedItem.getShelfmark());
        item.setPurchasePrice(updatedItem.getPurchasePrice());
        if (updatedItem.getExternalLibrary() != null && updatedItem.getExternalLibrary().getId() != null) {
            item.setExternalLibrary(updatedItem.getExternalLibrary());
        } else {
            item.setExternalLibrary(null);
        }

        item = this.save(item);

        return this.itemMapper.mapItemToItemDto(item);
    }

    @Override
    public ItemDto findOneDto(Integer id) {
        Item item = this.findOne(id);

        if (item != null) {
            this.linkRecord(item);
        }

        return this.itemMapper.mapItemToItemDto(item);
    }

    @Override
    public Item findOne(Integer integer) {
        Item item = super.findOne(integer);

        if (item != null) {
            this.linkRecord(item);
        }

        return item;
    }

    @Override
    public List<ItemDto> findAllDto() {
        List<Item> items = this.findAll();
        items.forEach(this::linkRecord);

        return mapLibrariesToLibrariesDTO(this.findAll());
    }

    @Override
    public ItemDto patchItem(JsonNode jsonNodeItem, Item item) throws InvalidRecordException, IllegalAccessException {

        itemMapper.mergeItemAndJsonNode(item, jsonNodeItem);
        return this.itemMapper.mapItemToItemDto(this.saveWithRecord(item));
    }

    @Override
    public ItemDto findByInternalId(Integer internalId) {

        Item item = this.linkRecord(itemDao.findByInternalId(internalId));
        return this.itemMapper.mapItemToItemDto(item);
    }

    @Override
    public Page<ItemDto> findAllPageableDto(Pageable pageable) {

        Page<Item> items = this.findAll(pageable);
        return this.mapItemsToItemDtosPage(items);
    }

    @Override
    public Page<ItemDto> findBySerialNumberAndSerialType(String serialNumber, String serialType, Pageable pageable)
            throws ISBNException {

        List<Integer> itemIds = new ArrayList<>();
        Page<Item> items = null;
        Page<ItemDto> itemDtos = null;

        Page<Record> records =
                this.solrRecordService.searchBySerialNumberAndSerialType(serialNumber, serialType, pageable);
        if (records.getContent().size() > 0) {
            HashMap<String, Record> recordMap = new HashMap<>();

            records.forEach(record -> {
                record.getItems().forEach(item -> itemIds.add(Integer.parseInt(item)));
                recordMap.put(record.getId(), record);
            });

            if (itemIds.size() > 0) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());
                items = this.itemDao.findByIdIn(itemIds, pageable);
                items.getContent().forEach(item -> item.setRecord(recordMap.get(item.getRecordId())));
            }
        }

        if (items != null) {
            itemDtos = this.mapItemsToItemDtosPage(items);
        }

        return itemDtos;
    }

    @Override
    public Page<ItemDto> findAllPageableDtoByRecordTitleMainTitle(Pageable pageable, String title) {
        List<Record> records = solrRecordService.searchByTitle(title);
        List<Integer> itemIds = new ArrayList<>();
        HashMap<String, Record> recordMap = new HashMap<>();

        records.forEach(record -> {
            recordMap.put(record.getId(), record);
            List<String> itemsIdStr = record.getItems();
            itemsIdStr.forEach(idStr -> {
                Integer id = Integer.parseInt(idStr);
                if (!itemIds.contains(id)) {
                    itemIds.add(id);
                }
            });
        });

        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());
        Page<Item> items = this.itemDao.findByIdIn(itemIds, pageable);
        items.getContent().forEach(item -> item.setRecord(recordMap.get(item.getRecordId())));

        return this.mapItemsToItemDtosPage(items);
    }

    @Override
    public Page<ItemDto> findAllItemDtoWithFilters(
            String title,
            String author,
            String publisher,
            String id,
            String publicationDate,
            Boolean borrowed,
            Pageable page
    ) {
        Page<Item> items =
                this.solrItemService.fullSearch(title, author, publisher, id, publicationDate, borrowed, page);

        return this.mapItemsToItemDtosPage(items);
    }

    @Override
    public Item linkRecord(Item item) {
        if (item.getRecordId() != null) {
            Record record = this.solrRecordService.findById(item.getRecordId());
            item.setRecord(record);
        }

        return item;
    }

    private Page<ItemDto> mapItemsToItemDtosPage(Page<Item> items) {
        List<ItemDto> itemDtos = new ArrayList<>();
        items.forEach(item -> itemDtos.add(this.itemMapper.mapItemToItemDto(item)));

        return new PageImpl<>(itemDtos);
    }

    private List<ItemDto> mapLibrariesToLibrariesDTO(List<Item> libraries) {
        List<ItemDto> librariesDto = new ArrayList<>();
        libraries.forEach(Item -> librariesDto.add(this.itemMapper.mapItemToItemDto(Item)));

        return librariesDto;
    }
}
