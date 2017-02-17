package org.lendi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import org.lendi.umtapo.dao.ItemDao;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.mapper.ItemMapper;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.ItemService;
import org.lendi.umtapo.solr.document.bean.record.Record;
import org.lendi.umtapo.solr.exception.InvalidRecordException;
import org.lendi.umtapo.solr.service.SolrRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
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
    public Item saveWithRecord(Item item) throws InvalidRecordException {
        if (item.getRecord() != null) {
            Record record = this.solrRecordService.save(item.getRecord());
            item.setRecordId(record.getId());
        }

        return this.save(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto saveDto(ItemDto itemDto) throws InvalidRecordException {

        Item item = this.itemMapper.mapItemDtoToItem(itemDto);
        if (item.getInternalId() == null) {
            Integer previousInternalId = this.itemDao.findTopInternalId();
            item.setInternalId(previousInternalId + 1);
        }
        item = this.saveWithRecord(item);

        return this.itemMapper.mapItemToItemDto(item);
    }

    @Override
    public Item findOne(Integer integer) {
        Item item = super.findOne(integer);
        this.linkRecord(item);

        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto findOneDto(Integer id) {
        Item item = this.findOne(id);

        return this.itemMapper.mapItemToItemDto(item);
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = super.findAll();
        items.forEach(this::linkRecord);

        return items;
    }

    @Override
    public List<ItemDto> findAllDto() {
        return mapItemsToItemsDto(this.findAll());
    }

    /**
     * {@inheritDoc}
     */
    public ItemDto patchItem(JsonNode jsonNodeItem, Item item)
            throws IOException, JsonPatchException, InvalidRecordException {

        itemMapper.mergeItemAndJsonNode(item, jsonNodeItem);
        return this.itemMapper.mapItemToItemDto(this.saveWithRecord(item));
    }

    private List<ItemDto> mapItemsToItemsDto(List<Item> items) {
        List<ItemDto> itemDtos = new ArrayList<>();
        items.forEach(item -> itemDtos.add(this.itemMapper.mapItemToItemDto(item)));

        return itemDtos;
    }

    private Item linkRecord(Item item) {
        if (item.getRecordId() != null) {
            Record record = this.solrRecordService.findById(item.getRecordId());
            item.setRecord(record);
        }

        return item;
    }
}
