package org.lendi.umtapo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.LoanDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.entity.Library;
import org.lendi.umtapo.entity.Loan;
import org.lendi.umtapo.service.specific.ItemService;
import org.lendi.umtapo.service.specific.LibraryService;
import org.lendi.umtapo.service.specific.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.time.ZonedDateTime;
import java.util.List;


/**
 * The type Loan web service.
 */
@RestController
public class LoanWebService {

    private static final Logger LOGGER = Logger.getLogger(LoanWebService.class);

    private final LoanService loanService;
    private final LibraryService libraryService;
    private final ItemService itemService;

    /**
     * Instantiates a new Loan web service.
     *
     * @param loanService    the loan service
     * @param libraryService the library service
     * @param itemService    the item service
     */
    public LoanWebService(LoanService loanService, LibraryService libraryService, ItemService itemService) {
        this.loanService = loanService;
        this.libraryService = libraryService;
        this.itemService = itemService;
    }

    /**
     * Gets loan.
     *
     * @param id the id
     * @return the loan
     */
    @RequestMapping(value = "/loans/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<LoanDto> getLoan(@PathVariable Integer id) {

        LoanDto loanDto = loanService.findOneDto(id);
        if (loanDto == null) {
            LOGGER.info("Loan with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loanDto, HttpStatus.OK);
    }

    /**
     * Gets loans.
     *
     * @param borrowerId the id
     * @return the loans
     */
    @RequestMapping(value = "/loans", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getLoans(@PathParam("borrowerId") Integer borrowerId) {

        List<LoanDto> loans;

        if (borrowerId != null) {
            loans = loanService.findAllDtoByBorrowerIdAndNotReturned(borrowerId);
        } else {
            loans = loanService.findAllDto();
        }

        if (loans == null || loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    /**
     * Sets loan.
     *
     * @param loanDto the loan dto
     * @return the loan
     */
    @RequestMapping(value = "/loans", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LoanDto> setLoan(@RequestBody LoanDto loanDto) {

        if (loanDto.getItem() != null && loanDto.getBorrower() != null
                && loanDto.getItem().getId() != null && loanDto.getBorrower().getId() != null) {

            if (loanDto.getReturned() == null) {
                loanDto.setReturned(false);
            }
            Item item = this.itemService.findOne(loanDto.getItem().getId());

            if (item.getBorrowed()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                item.setBorrowed(true);
                this.itemService.save(item);

                if (loanDto.getStart() == null) {
                    loanDto.setStart(ZonedDateTime.now());
                }

                if (loanDto.getEnd() == null) {
                    Library library = this.libraryService.findOne(item.getLibrary().getId());
                    ZonedDateTime end = ZonedDateTime.now();
                    end = end.plusDays(library.getBorrowDuration());
                    loanDto.setEnd(end);
                }
                loanDto = loanService.saveDto(loanDto);

                return new ResponseEntity<>(loanDto, HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Patch response entity.
     *
     * @param jsonNodeLoan the json node loan
     * @param id           the id
     * @return the response entity
     */
    @RequestMapping(value = "/loans/{id}", method = RequestMethod.PATCH, consumes = "application/json", produces = {
            "application/json", "application/json-patch+json"})
    public ResponseEntity patch(@RequestBody JsonNode jsonNodeLoan, @PathVariable Integer id) {

        Loan loan = loanService.findOne(id);
        if (loan == null) {
            return new ResponseEntity<>("This loan do not exist", HttpStatus.NO_CONTENT);
        } else {
            try {
                loanService.patchLoan(jsonNodeLoan, loan);
            } catch (final IllegalAccessException e) {
                LOGGER.error("JsonPatch Error" + e);
                return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
