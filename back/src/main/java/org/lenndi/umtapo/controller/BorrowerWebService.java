package org.lenndi.umtapo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.lenndi.umtapo.dto.BorrowerDto;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.service.specific.BorrowerService;
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
import java.util.NoSuchElementException;

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
     * @param page         the page
     * @param size         the size
     * @param nameOrEmail  the name or email
     * @param name         the contains
     * @param id           the id
     * @param email        the email
     * @param city         the city
     * @param lateness     the lateness
     * @param tooMuchLoans the too much loans
     * @param sort         the sort
     * @param order        the order
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
                                       @PathParam("lateness") Boolean lateness,
                                       @PathParam("tooMuchLoans") Boolean tooMuchLoans,
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
            if (order.equals("DESC")) {
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
                    name, email, city, id, tooMuchLoans, lateness, pageable);
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

        borrowerDto.setActive(true);
        borrowerDto = this.borrowerService.saveDto(borrowerDto);

        return new ResponseEntity<>(borrowerDto, HttpStatus.CREATED);
    }

    /**
     * Patch response entity.
     *
     * @param jsonNodeBorrower the json node borrower
     * @param id               the id
     * @return the response entity
     */
    @RequestMapping(value = "/borrowers/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json-patch+json"})
    public ResponseEntity patch(@RequestBody JsonNode jsonNodeBorrower, @PathVariable Integer id) {

        Borrower borrower = borrowerService.findOne(id);
        if (borrower == null) {
            return new ResponseEntity<>("This borrower do not exist", HttpStatus.NOT_FOUND);
        } else {
            borrowerService.patchBorrower(jsonNodeBorrower, borrower);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Update borrower.
     *
     * @param borrower the borrower
     * @param id       the id
     * @return 200 if updated without error
     */
    @RequestMapping(value = "/borrowers/{id}",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateBorrower(@RequestBody BorrowerDto borrower, @PathVariable Integer id) {

        if (!id.equals(borrower.getId()) || this.borrowerService.findOne(id) == null) {
            return new ResponseEntity<>("This borrower does not exist", HttpStatus.NOT_FOUND);
        }
        this.borrowerService.updateDto(borrower);

        return new ResponseEntity<>(borrower, HttpStatus.NO_CONTENT);
    }

    /**
     * Delete borrower setting activate field to false.
     *
     * @param id the id
     * @return 204 response entity
     */
    @RequestMapping(value = "borrowers/{id}",
            method = RequestMethod.DELETE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteBorrower(@PathVariable Integer id) {

        try {
            this.borrowerService.delete(id);

            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (final NoSuchElementException e) {
            return new ResponseEntity<>("Borrower not found", HttpStatus.NOT_FOUND);
        }
    }
}
