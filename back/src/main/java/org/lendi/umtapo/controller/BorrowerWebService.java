package org.lendi.umtapo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.rest.ApiError;
import org.lendi.umtapo.service.specific.BorrowerService;
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

/**
 * The type Borrower web service.
 */
@RestController
public class BorrowerWebService {

    private static final Logger LOGGER = Logger.getLogger(BorrowerWebService.class);
    private static final Integer DEFAULT_PAGE_SIZE = 100;

    private final BorrowerService borrowerService;

    /**
     * Instantiates a new Borrower web service.
     *
     * @param borrowerService the borrower service
     */
    @Autowired
    public BorrowerWebService(BorrowerService borrowerService) {
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
     * @param page                the page
     * @param size                the size
     * @param nameOrEmail         the name or email
     * @param name                the contains
     * @param id                  the id
     * @param email               the email
     * @param city                the city
     * @param sort                the sort
     * @param order               the order
     * @return the borrowers
     */
    @RequestMapping(value = "/borrowers", method = RequestMethod
            .GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getBorrowers(@PathParam("page") Integer page,
                                       @PathParam("size") Integer size,
                                       @PathParam("nameOrEmail") String nameOrEmail,
                                       @PathParam("name") String name,
                                       @PathParam("id") String id,
                                       @PathParam("email") String email,
                                       @PathParam("city") String city,
                                       @PathParam("sort") String sort,
                                       @PathParam("order") String order) {

        Page<BorrowerDto> borrowerDtos;
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = DEFAULT_PAGE_SIZE;
        }

        Pageable pageable;
        if (sort != null) {
            if (order.equals("desc")) {
                pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, sort));
            } else {
                pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, sort));
            }
        } else {
            pageable = new PageRequest(page, size);
        }

        if (nameOrEmail != null) {
            borrowerDtos = borrowerService.findAllBorrowerDtoByNameOrEmail(nameOrEmail, pageable);
        } else if (name == null && id == null && email == null && city == null) {
            borrowerDtos = this.borrowerService.findAllDto(pageable);
        } else {
            if (name == null) {
                name = "";
            }
            if (email == null) {
                email = "";
            }
            if (id == null) {
                id = "";
            }
            if (city == null) {
                city = "";
            }

            borrowerDtos = borrowerService.findAllBorrowerDtoWithFilters(
                    name, email, city, id, pageable);
        }

        if (borrowerDtos.getTotalElements() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(borrowerDtos, HttpStatus.OK);
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

        try {
            borrowerDto = borrowerService.saveDto(borrowerDto);
        } catch (final InvalidRecordException e) {
            LOGGER.fatal(e.getMessage());
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "Invalid record");

            return new ResponseEntity<>(apiError, apiError.getStatus());
        }

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
            } catch (final IllegalAccessException e) {
                LOGGER.error("JsonPatch Error" + e);
                return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
