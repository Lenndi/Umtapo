package org.lendi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.service.specific.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Item Webservice.
 * <p>
 * Created by axel on 10/01/17.
 */
@RestController
public class ItemWebService {

    private static final Logger LOGGER = Logger.getLogger(ItemWebService.class);

    @Autowired
    private ItemService itemService;

    /**
     * Gets item.
     *
     * @param id the id
     * @return the item
     */
    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ItemDto> getItem(@PathVariable Integer id) {

        ItemDto itemDto = itemService.findOneDto(id);
        if (itemDto == null) {
            LOGGER.info("Item with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    /**
     * Get items.
     *
     * @return the items
     */
    @RequestMapping(value = "/items", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getItems() {

        List<ItemDto> itemDtos = itemService.findAllDto();

        if (itemDtos == null || itemDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity(itemDtos, HttpStatus.OK);
    }

    /**
     * Sets item.
     * Validators.required
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
}
