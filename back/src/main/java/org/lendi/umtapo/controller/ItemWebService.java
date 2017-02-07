package org.lendi.umtapo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.rest.ApiError;
import org.lendi.umtapo.service.specific.ItemService;
import org.lendi.umtapo.solr.exception.InvalidRecordException;
import org.springframework.beans.factory.annotation.Autowired;
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(itemDto, HttpStatus.OK);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>("This item do not exist", HttpStatus.NOT_FOUND);
        } else {
            if (item.isBorrowed() != null) {
                if (jsonNodeItem.get("isBorrowed") != null && item.isBorrowed() != null) {
                    if (item.isBorrowed() == jsonNodeItem.get("isBorrowed").asBoolean()) {
                        if (!item.isBorrowed()) {
                            return new ResponseEntity<>("This item is already not borrowed", HttpStatus.NOT_MODIFIED);
                        } else if (item.isBorrowed()) {
                            return new ResponseEntity<>("This item is already borrowed", HttpStatus.NOT_MODIFIED);
                        }
                    }
                }
            }
            try {
                itemService.patchItem(jsonNodeItem, item);
            } catch (final IOException | JsonPatchException e) {
                LOGGER.error("JsonPatch Error" + e);
                return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (final InvalidRecordException e) {
                LOGGER.fatal(e.getMessage());
                ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "Invalid record");

                return new ResponseEntity<>(apiError, apiError.getStatus());
            }
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}