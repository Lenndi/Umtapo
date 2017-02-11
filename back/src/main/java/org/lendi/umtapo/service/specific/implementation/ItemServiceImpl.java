package org.lendi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import org.lendi.umtapo.dao.ItemDao;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.mapper.ItemMapper;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.ItemService;
import org.lendi.umtapo.solr.document.record.Identifier;
import org.lendi.umtapo.solr.document.record.RecordDocument;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
import org.lendi.umtapo.solr.service.SolrRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto saveDto(ItemDto itemDto) throws SolrRepositoryException {

        if (itemDto.getRecordId() == null) {
            RecordDocument recordDocument = itemDto.getRecord();
            if (recordDocument != null && recordDocument.getId() == null) {
                Identifier identifier = recordDocument.getIdentifier();
                List<RecordDocument> documents;
                documents = this.solrRecordService.searchBySerialNumber(
                        identifier.getSerialNumber(),
                        identifier.getSerialType());

                if (documents.size() < 1) {
                    recordDocument.setId(UUID.randomUUID().toString());
                    this.solrRecordService.addToIndex(recordDocument);
                } else {
                    recordDocument = documents.get(0);
                }
            }
            itemDto.setRecordId(recordDocument.getId());
        }

        Item item = this.itemMapper.mapItemDtoToItem(itemDto);
        if (item.getInternalId() == null) {
            Integer previousInternalId = this.itemDao.findTopInternalId();
            item.setInternalId(previousInternalId + 1);
        }
        item = this.save(item);

        return this.itemMapper.mapItemToItemDto(item);
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
    public List<ItemDto> findAllDto() {
        return mapLibrariesToLibrariesDTO(this.findAll());
    }

    /**
     * {@inheritDoc}
     */
    public ItemDto patchItem(JsonNode jsonNodeItem, Item item) throws IOException, JsonPatchException {

        itemMapper.mergeItemAndJsonNode(item, jsonNodeItem);
        return this.mapItemToItemDto(this.save(item));
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
