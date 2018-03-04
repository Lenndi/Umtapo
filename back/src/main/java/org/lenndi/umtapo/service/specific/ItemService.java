package org.lenndi.umtapo.service.specific;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.ladutsko.isbn.ISBNException;
import org.lenndi.umtapo.dto.ItemDto;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.service.generic.GenericService;
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
     */
    Item saveWithRecord(Item item);

    /**
     * Persist a Item from a ItemDto.
     *
     * @param itemDto the item dto
     * @return item dto
     */
    ItemDto saveDto(ItemDto itemDto);

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
     */
    ItemDto patchItem(JsonNode jsonNodeItem, Item item);

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
     * @throws ISBNException the isbn exception
     */
    Page<ItemDto> findBySerialNumberAndSerialType(String serialNumber, String serialType, Pageable pageable)
            throws ISBNException;

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
}
