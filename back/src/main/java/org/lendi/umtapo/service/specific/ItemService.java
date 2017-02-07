package org.lendi.umtapo.service.specific;

import com.fasterxml.jackson.databind.JsonNode;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.service.generic.GenericService;
import org.lendi.umtapo.solr.exception.InvalidRecordException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * The interface of Item service.
 */
@Service
public interface ItemService extends GenericService<Item, Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    Item save(Item item);

    /**
     * Persist an Item with the associated record.
     *
     * @param item item to persist
     * @return item
     */
    Item saveWithRecord(Item item) throws InvalidRecordException;

    /**
     * Persist a Item from a ItemDto.
     *
     * @param itemDto the item dto
     * @return item dto
     * @throws InvalidRecordException invalid record
     */
    ItemDto saveDto(ItemDto itemDto) throws InvalidRecordException;

    /**
     * {@inheritDoc}
     */
    @Override
    Item findOne(Integer id);

    /**
     * Find a Item by id.
     *
     * @param id the id
     * @return ItemDto item dto
     */
    ItemDto findOneDto(Integer id);

    /**
     * {@inheritDoc}
     */
    @Override
    List<Item> findAll();

    /**
     * Find all Libraries.
     *
     * @return list list
     */
    List<ItemDto> findAllDto();


    /**
     * {@inheritDoc}
     */
    @Override
    Boolean exists(Integer id);

    /**
     * Dynamic patch borrower.
     *
     * @param jsonNodeItem the json node item
     * @param item         the item
     * @return the borrower
     */
    ItemDto patchItem(JsonNode jsonNodeItem, Item item);

    /**
     * Find by internal id item dto.
     *
     * @param internalId the internal id
     * @return the item dto
     */
    public ItemDto findByInternalId(Integer internalId);
}
