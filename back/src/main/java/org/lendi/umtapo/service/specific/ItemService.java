package org.lendi.umtapo.service.specific;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
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
    Item save(Item library);

    /**
     * Save with document item.
     *
     * @param item the item
     * @return the item
     * @throws InvalidRecordException the invalid record exception
     */
    Item saveWithDocument(Item item) throws InvalidRecordException;

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
     * @throws IOException            the io exception
     * @throws JsonPatchException     the json patch exception
     * @throws InvalidRecordException the invalid record exception
     */
    ItemDto patchItem(JsonNode jsonNodeItem, Item item) throws IOException, JsonPatchException, InvalidRecordException;

}
