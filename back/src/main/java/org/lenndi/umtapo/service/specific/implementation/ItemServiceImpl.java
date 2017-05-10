package org.lenndi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import org.lenndi.umtapo.dao.ItemDao;
import org.lenndi.umtapo.dto.ItemDto;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.mapper.ItemMapper;
import org.lenndi.umtapo.service.generic.AbstractGenericService;
import org.lenndi.umtapo.service.specific.ItemService;
import org.lenndi.umtapo.solr.document.bean.record.Identifier;
import org.lenndi.umtapo.solr.document.bean.record.Record;
import org.lenndi.umtapo.solr.exception.InvalidRecordException;
import org.lenndi.umtapo.solr.service.SolrRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

    /**
     * Instantiates a new Item service.
     *
     * @param itemMapper        the item mapper
     * @param itemDao           the item dao
     * @param solrRecordService the solr record service
     */
    @Autowired
    public ItemServiceImpl(ItemMapper itemMapper, ItemDao itemDao, SolrRecordService solrRecordService) {
        Assert.notNull(itemMapper);
        Assert.notNull(itemDao);
        Assert.notNull(solrRecordService);

        this.itemMapper = itemMapper;
        this.itemDao = itemDao;
        this.solrRecordService = solrRecordService;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto saveDto(ItemDto itemDto) throws InvalidRecordException {
        Item item = this.itemMapper.mapItemDtoToItem(itemDto);
        item = this.saveWithRecord(item);

        return this.itemMapper.mapItemToItemDto(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto findOneDto(Integer id) {
        Item item = this.findOne(id);

        if (item != null) {
            this.linkRecord(item);
        }

        return this.itemMapper.mapItemToItemDto(item);
    }

    @Override
    public List<ItemDto> findAllDto() {
        List<Item> items = this.findAll();
        items.forEach(this::linkRecord);

        return mapLibrariesToLibrariesDTO(this.findAll());
    }

    /**
     * {@inheritDoc}
     */
    public ItemDto patchItem(JsonNode jsonNodeItem, Item item) throws InvalidRecordException, IllegalAccessException {

        itemMapper.mergeItemAndJsonNode(item, jsonNodeItem);
        return this.mapItemToItemDto(this.saveWithRecord(item));
    }

    /**
     * {@inheritDoc}
     */
    public ItemDto findByInternalId(Integer internalId) {

        Item item = itemDao.findByInternalId(internalId);
        return this.itemMapper.mapItemToItemDto(item);
    }

    /**
     * {@inheritDoc}
     */
    public Page<ItemDto> findAllPageableDto(Pageable pageable) {

        Page<Item> items = this.findAll(pageable);
        return this.mapItemsToItemDtosPage(items);
    }

    /**
     * {@inheritDoc}
     */
    public Page<ItemDto> findBySerialNumberAndSerialType(String serialNumber, String serialType, Pageable pageable) {

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
                items = this.itemDao.findByIdIn(itemIds, pageable);
                items.getContent().forEach(item -> item.setRecord(recordMap.get(item.getRecordId())));
            }
        }

        if (items != null) {
            itemDtos = this.mapItemsToItemDtosPage(items);
        }

        return itemDtos;
    }

    /**
     * {@inheritDoc}
     */
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
            Pageable page
    ) {
        List<ItemDto> items = new ArrayList<>();
        Page<Record> records =
                this.solrRecordService.fullSearch(title, author, publisher, id, publicationDate, page);

        records.getContent().forEach(record -> record.getItems().forEach(itemId -> {
            items.add(new ItemDto(Integer.parseInt(itemId), record));
        }));

        return new PageImpl<>(items, page, records.getTotalElements());
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
        items.forEach(item -> itemDtos.add(mapItemToItemDto(item)));

        return new PageImpl<>(itemDtos);
    }

    private List<ItemDto> mapItemsToItemsDto(List<Item> items) {
        List<ItemDto> itemDtos = new ArrayList<>();
        items.forEach(item -> itemDtos.add(this.itemMapper.mapItemToItemDto(item)));

        return itemDtos;
    }

    private Item mapItemDtoToItem(ItemDto itemDto) {
        return this.itemMapper.mapItemDtoToItem(itemDto);
    }

    private ItemDto mapItemToItemDto(Item item) {
        return this.itemMapper.mapItemToItemDto(item);
    }

    private List<Item> mapLibrariesDtoToLibraries(List<ItemDto> librariesDto) {
        List<Item> libraries = new ArrayList<>();
        librariesDto.forEach(ItemDto -> libraries.add(mapItemDtoToItem(ItemDto)));

        return libraries;
    }

    private List<ItemDto> mapLibrariesToLibrariesDTO(List<Item> libraries) {
        List<ItemDto> librariesDto = new ArrayList<>();
        libraries.forEach(Item -> librariesDto.add(mapItemToItemDto(Item)));

        return librariesDto;
    }
}
