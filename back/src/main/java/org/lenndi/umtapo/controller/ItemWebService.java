package org.lenndi.umtapo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.ladutsko.isbn.ISBNException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.lenndi.umtapo.dto.ItemDto;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.enumeration.ApplicationCodeEnum;
import org.lenndi.umtapo.service.specific.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Item web service.
 */
@RestController
public class ItemWebService {

    private static final Logger LOGGER = Logger.getLogger(ItemWebService.class);
    private static final Integer DEFAULT_SIZE = 100;

    private final ItemService itemService;

    /**
     * Instantiates a new Item web service.
     *
     * @param itemService the item service
     */
    @Autowired
    public ItemWebService(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Gets item.
     *
     * @param id the id
     * @return the item
     */
    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<ItemDto> getItem(@PathVariable Integer id) {

        ItemDto itemDto = this.itemService.findOneDto(id);
        if (itemDto == null) {
            LOGGER.info("Item with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    /**
     * Gets items.
     *
     * @param page the page
     * @param size the size
     * @return the items
     */
    @RequestMapping(value = "/items", method = RequestMethod
            .GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getItems(@PathParam("page") Integer page, @PathParam("size") Integer size) {

        if (size != null && page != null) {
            Page<ItemDto> itemDtoPage;
            Pageable pageable = new PageRequest(page, size, new Sort("id"));
            itemDtoPage = itemService.findAllPageableDto(pageable);
            if (itemDtoPage == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
            } else {
                return new ResponseEntity<>(itemDtoPage, HttpStatus.OK);
            }
        } else {
            List<ItemDto> itemDtos;
            itemDtos = itemService.findAllDto();

            if (itemDtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
            } else {
                return new ResponseEntity<>(itemDtos, HttpStatus.NO_CONTENT);
            }
        }
    }

    /**
     * Gets item.
     *
     * @param page            the page
     * @param size            the size
     * @param sort            the sort
     * @param order           the order
     * @param complexSearch   the complex search
     * @param serialNumber    the serial number
     * @param serialType      the serial type
     * @param mainTitle       the main title
     * @param author          the author
     * @param publisher       the publisher
     * @param id              the id
     * @param publicationDate the publication date
     * @param borrowed        the borrowed
     * @return the item
     */
    @RequestMapping(value = "/items/searchs", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getItemSearchs(@PathParam("page") Integer page,
                                         @PathParam("size") Integer size,
                                         @PathParam("sort") String sort,
                                         @PathParam("order") String order,
                                         @PathParam("complexSearch") Boolean complexSearch,
                                         @PathParam("serialNumber") String serialNumber,
                                         @PathParam("serialType") String serialType,
                                         @PathParam("mainTitle") String mainTitle,
                                         @PathParam("author") String author,
                                         @PathParam("publisher") String publisher,
                                         @PathParam("id") String id,
                                         @PathParam("publicationDate") String publicationDate,
                                         @PathParam("borrowed") Boolean borrowed) {

        Page<ItemDto> itemDtos = null;

        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = DEFAULT_SIZE;
        }

        Pageable pageable;
        if (sort != null) {
            if (order.equals("DESC")) {
                pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, sort));
            } else {
                pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, sort));
            }
        } else {
            pageable = new PageRequest(page, size);
        }

        if (!complexSearch && (StringUtils.isNotBlank(serialNumber) && StringUtils.isNotBlank(serialType))) {
            try {
                itemDtos = this.itemService.findBySerialNumberAndSerialType(serialNumber, serialType, pageable);
            } catch (final ISBNException e) {
                LOGGER.error(e.getMessage());
                return new ResponseEntity<>("Bad ISBN format", HttpStatus.BAD_REQUEST);
            }
        } else if (complexSearch) {
            if (mainTitle == null) {
                mainTitle = "";
            }
            if (author == null) {
                author = "";
            }
            if (id == null) {
                id = "";
            }
            if (publisher == null) {
                publisher = "";
            }
            if (publicationDate == null) {
                publicationDate = "";
            }
            itemDtos = this.itemService.findAllItemDtoWithFilters(
                    mainTitle, author, publisher, id, publicationDate, borrowed, pageable);
        } else if (StringUtils.isNotBlank(mainTitle)) {
            itemDtos = this.itemService.findAllPageableDtoByRecordTitleMainTitle(pageable, mainTitle);
        }

        if (itemDtos == null) {
            LOGGER.info("Items not found");
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }

    /**
     * Gets item.
     *
     * @param internalId the internal id
     * @return the item
     */
    @RequestMapping(value = "/items/search", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<ItemDto> getItemSearch(@PathParam("internalId") Integer internalId) {

        ItemDto itemDto = null;

        if (internalId != null) {
            itemDto = this.itemService.findByInternalId(internalId);
        }
        if (itemDto == null) {
            LOGGER.info("Item with id " + internalId + " not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    /**
     * Sets item.
     *
     * @param itemDto the item dto
     * @return the item
     */
    @RequestMapping(value = "/items", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity setItem(@RequestBody ItemDto itemDto) {

        if (itemDto.getBorrowed() == null) {
            itemDto.setBorrowed(false);
        }

        itemDto = itemService.saveDto(itemDto);

        return new ResponseEntity<>(itemDto, HttpStatus.CREATED);
    }

    /**
     * Update item response entity.
     *
     * @param itemDto the item dto
     * @return the response entity
     */
    @RequestMapping(value = "/items", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateItem(@RequestBody ItemDto itemDto) {
        itemDto = itemService.updateDto(itemDto);
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    /**
     * Sets item.
     *
     * @param jsonNodeItem the json node item
     * @param id           the id
     * @return the item
     */
    @RequestMapping(value = "/items/{id}", method = RequestMethod.PATCH, consumes = "application/json", produces = {
            "application/json", "application/json-patch+json"})
    public ResponseEntity patch(@RequestBody JsonNode jsonNodeItem, @PathVariable Integer id) {

        Item item = itemService.findOne(id);
        if (item == null) {
            return new ResponseEntity<>("This item do not exist", HttpStatus.NO_CONTENT);
        } else {
            if (item.getBorrowed() != null) {
                if (jsonNodeItem.get("borrowed") != null && item.getBorrowed() != null) {
                    if (item.getBorrowed() == jsonNodeItem.get("borrowed").asBoolean()) {
                        if (!item.getBorrowed()) {
                            return new ResponseEntity<>(ApplicationCodeEnum.DOCUMENT_ALREADY_RENDERED.getValue(),
                                    HttpStatus.ACCEPTED);
                        } else if (item.getBorrowed()) {
                            return new ResponseEntity<>(ApplicationCodeEnum.DOCUMENT_ALREADY_BORROWED.getValue(),
                                    HttpStatus.ACCEPTED);
                        }
                    }
                } else if (item.getLoanable() == null || !item.getLoanable()) {
                    return new ResponseEntity<>(ApplicationCodeEnum.DOCUMENT_ALREADY_BORROWED.getValue(), HttpStatus
                            .ACCEPTED);
                }
            }
            itemService.patchItem(jsonNodeItem, item);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
