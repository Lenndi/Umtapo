package org.lenndi.umtapo.service.specific;

import com.fasterxml.jackson.databind.JsonNode;
import org.lenndi.umtapo.dto.ItemDto;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.service.generic.GenericService;
import org.lenndi.umtapo.solr.exception.InvalidRecordException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface of Item service.
 */
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
     * @return item item
     * @throws InvalidRecordException the invalid record exception
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
     * Update dto item dto.
     *
     * @param itemDto the item dto
     * @return the item dto
     */
    ItemDto updateDto(ItemDto itemDto);

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
     * @throws InvalidRecordException the invalid record exception
     * @throws IllegalAccessException the illegal access exception
     */
    ItemDto patchItem(JsonNode jsonNodeItem, Item item) throws InvalidRecordException, IllegalAccessException;

    /**
     * Find by internal id item dto.
     *
     * @param internalId the internal id
     * @return the item dto
     */
    ItemDto findByInternalId(Integer internalId);

    /**
     * Find all pageable dto page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<ItemDto> findAllPageableDto(Pageable pageable);

    /**
     * Find all pageable dto by record identifier serial number page.
     *
     * @param serialNumber the serial number
     * @param serialType   the serial type
     * @param pageable     the pageable
     * @return the page
     */
    Page<ItemDto> findBySerialNumberAndSerialType(String serialNumber, String serialType, Pageable pageable);

    /**
     * Find all pageable dto by record titel main title page.
     *
     * @param pageable the pageable
     * @param contains the contains
     * @return the page
     */
    Page<ItemDto> findAllPageableDtoByRecordTitleMainTitle(Pageable pageable, String contains);

    /**
     * Find all item dto with filters page.
     *
     * @param title           the title
     * @param author          the author
     * @param publisher       the publisher
     * @param id              the id
     * @param publicationDate the publication date
     * @param borrowed        true if the item is borrowed
     * @param page            the page
     * @return the page
     */
    Page<ItemDto> findAllItemDtoWithFilters(
            String title,
            String author,
            String publisher,
            String id,
            String publicationDate,
            Boolean borrowed,
            Pageable page);

    /**
     * Link record item.
     *
     * @param item the item
     * @return the item
     */
    Item linkRecord(Item item);

    /**
     * Find by nfc id item.
     *
     * @param nfcId the nfc id
     * @return the item
     */
    Item findByNfcId(String nfcId);

}
