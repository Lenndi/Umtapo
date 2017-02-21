package org.lendi.umtapo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.enumeration.ApplicationCodeEnum;
import org.lendi.umtapo.rest.ApiError;
import org.lendi.umtapo.service.specific.ItemService;
import org.lendi.umtapo.solr.exception.InvalidRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Item web service.
 */
@RestController
public class ItemWebService {

    private static final Logger LOGGER = Logger.getLogger(ItemWebService.class);

    private final ItemService itemService;

    /**
     * Instantiates a new Item web service.
     *
     * @param itemService the item service
     */
    @Autowired
    public ItemWebService(ItemService itemService) {
        Assert.notNull(itemService, "Argument itemService cannot be null.");
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
     * @param page      the page
     * @param size      the size
     * @param contains  the contains
     * @param attribute the attribute
     * @return the item
     */
    @RequestMapping(value = "/items/searchs", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<ItemDto> getItemSearchs(@PathParam("page") Integer page,
                                                  @PathParam("size") Integer size,
                                                  @PathParam("contains") String contains,
                                                  @PathParam("attribute") String attribute) {

        Page<ItemDto> itemDtos = null;

        if (size != null && page != null && contains != null) {
            Pageable pageable = new PageRequest(page, size, new Sort("id"));
            if (Objects.equals(attribute, "barCode")) {
                itemDtos = this.itemService.findAllPageableDtoByRecordIdentifierBarCode(pageable, contains);
            } else if (Objects.equals(attribute, "mainTitle")) {
                itemDtos = this.itemService.findAllPageableDtoByRecordTitelMainTitle(pageable, contains);
            }
        }
        if (itemDtos == null) {
            LOGGER.info("Items not found");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(itemDtos, HttpStatus.OK);
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

        try {
            itemDto = itemService.saveDto(itemDto);
        } catch (final InvalidRecordException e) {
            LOGGER.fatal(e.getMessage());
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "Invalid record");

            return new ResponseEntity<>(apiError, apiError.getStatus());
        }

        return new ResponseEntity<>(itemDto, HttpStatus.CREATED);
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
            if (item.isBorrowed() != null) {
                if (jsonNodeItem.get("isBorrowed") != null && item.isBorrowed() != null) {
                    if (item.isBorrowed() == jsonNodeItem.get("isBorrowed").asBoolean()) {
                        if (!item.isBorrowed()) {
                            return new ResponseEntity<>(ApplicationCodeEnum.DOCUMENT_ALREADY_RENDERED.getValue(), HttpStatus.ACCEPTED);
                        } else if (item.isBorrowed()) {
                            return new ResponseEntity<>(ApplicationCodeEnum.DOCUMENT_ALREADY_BORROWED.getValue(), HttpStatus.ACCEPTED);
                        }
                    }
                } else if (item.getLoanable() == null || !item.getLoanable()){
                    return new ResponseEntity<>(ApplicationCodeEnum.DOCUMENT_ALREADY_BORROWED.getValue(), HttpStatus
                            .ACCEPTED);
                }
            }
            try {
                itemService.patchItem(jsonNodeItem, item);
            } catch (final InvalidRecordException e) {
                LOGGER.fatal(e.getMessage());
                ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "Invalid record");

                return new ResponseEntity<>(apiError, apiError.getStatus());
            }
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}