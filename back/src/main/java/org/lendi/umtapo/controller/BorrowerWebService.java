package org.lendi.umtapo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.lendi.umtapo.util.JsonViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

/**
 * The type Borrower web service.
 */
@RestController
@CrossOrigin
public class BorrowerWebService {

    private static final Logger LOGGER = Logger.getLogger(BorrowerWebService.class);

    private final BorrowerService borrowerService;

    private final JsonViewResolver jsonViewResolver;


    /**
     * Instantiates a new Borrower web service.
     *
     * @param borrowerService  the borrower service
     * @param jsonViewResolver the json view resolver
     */
    @Autowired
    public BorrowerWebService(BorrowerService borrowerService, JsonViewResolver jsonViewResolver) {
        this.jsonViewResolver = jsonViewResolver;
        Assert.notNull(borrowerService);
        this.borrowerService = borrowerService;
    }

    /**
     * Gets borrower.
     *
     * @param id the id
     * @return the borrower
     */
    @RequestMapping(value = "/borrowers/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity getBorrower(@PathVariable Integer id) {

        BorrowerDto borrowerDto;
        borrowerDto = borrowerService.findOneDto(id);
        if (borrowerDto == null) {
            LOGGER.info("Borrower with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(borrowerDto, HttpStatus.OK);
    }


    /**
     * Gets borrowers.
     *
     * @param page     the page
     * @param size     the size
     * @param contains the contains
     * @return the borrowers
     */
    @RequestMapping(value = "/borrowers", method = RequestMethod
            .GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getBorrowers(@PathParam("page") Integer page, @PathParam("size") Integer size,
                                       @PathParam("contains") String contains) {

        if (size != null && page != null) {
            Page<BorrowerDto> borrowerDtos;
            Pageable pageable = new PageRequest(page, size, new Sort("id"));

            if (contains != null) {
                borrowerDtos = borrowerService.findAllPageableDto(pageable, contains);
            } else {
                borrowerDtos = borrowerService.findAllPageableDto(pageable, "");
            }

            if (borrowerDtos == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
            } else {
                return new ResponseEntity<>(borrowerDtos, HttpStatus.OK);
            }
        } else {
            List<BorrowerDto> borrowerDtos;
            borrowerDtos = borrowerService.findAllDto();

            if (borrowerDtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
            } else {
                return new ResponseEntity<>(borrowerDtos, HttpStatus.NOT_FOUND);
            }
        }

    }

    /**
     * Sets borrower.
     *
     * @param borrowerDto the borrower dto
     * @return the borrower
     */
    @RequestMapping(value = "/borrowers", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity setBorrower(@RequestBody BorrowerDto borrowerDto) {

        borrowerDto = borrowerService.saveDto(borrowerDto);

        return new ResponseEntity<>(borrowerDto, HttpStatus.CREATED);
    }

    /**
     * Patch response entity.
     *
     * @param jsonNodeBorrower the json node borrower
     * @param id               the id
     * @return the response entity
     */
    @RequestMapping(value = "/borrowers/{id}", method = RequestMethod.PATCH, consumes = "application/json", produces = {
            "application/json", "application/json-patch+json"})
    public ResponseEntity patch(@RequestBody JsonNode jsonNodeBorrower, @PathVariable Integer id) {

        Borrower borrower = borrowerService.findOne(id);
        if (borrower == null) {
            return new ResponseEntity<>("This borrower do not exist", HttpStatus.NOT_FOUND);
        } else {
            try {
                borrowerService.patchBorrower(jsonNodeBorrower, borrower);
            } catch (final IOException | JsonPatchException e) {
                LOGGER.error("JsonPatch Error" + e);
                return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
