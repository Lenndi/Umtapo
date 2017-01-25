package org.lendi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.service.specific.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Item web service.
 */
@RestController
@CrossOrigin
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
     * Sets item.
     *
     * @param itemDto the item dto
     * @return the item
     */
    @RequestMapping(value = "/items", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ItemDto> setItem(@RequestBody ItemDto itemDto) {

        itemDto = itemService.saveDto(itemDto);
        return new ResponseEntity<>(itemDto, HttpStatus.CREATED);
    }

    /**
     * Sets item.
     *
     * @param itemDto the item dto
     * @return the item
     */
    @RequestMapping(value = "/items", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity patchItem(@RequestBody ItemDto itemDto) {

        if(itemDto.getId() != null) {
            if (itemDto.getCondition() != null) {
                if (itemService.saveCondition(itemDto) == null) {
                    return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<>("Item modified", HttpStatus.OK);
                }
            } else if (itemDto.getInternalId() != null) {
                if (itemService.saveInternalId(itemDto) == null) {
                    return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<>("Item modified", HttpStatus.OK);
                }
            }
        } else {
            return new ResponseEntity<>("Id cannot be null", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Patch error", HttpStatus.OK);
    }
}
