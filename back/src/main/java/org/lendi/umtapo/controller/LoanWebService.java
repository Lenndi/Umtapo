package org.lendi.umtapo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.LoanDto;
import org.lendi.umtapo.entity.Loan;
import org.lendi.umtapo.service.specific.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
 * The type Loan web service.
 */
@RestController
public class LoanWebService {

    private static final Logger LOGGER = Logger.getLogger(LoanWebService.class);

    private final LoanService loanService;

    /**
     * Instantiates a new Loan web service.
     *
     * @param loanService the loan service
     */
    public LoanWebService(LoanService loanService) {
        this.loanService = loanService;
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
     * @param id the id
     * @return the loans
     */
    @RequestMapping(value = "/loans", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getLoans(@PathParam("id") Integer id, @PathParam("page") Integer page, @PathParam("size") Integer size,
                                   @PathParam("contains") String contains) {

        List<LoanDto> loans;

        if (id != null) {
            loans = loanService.findAllDtoByBorrowerIdAndReturned(id);
        } else {
            loans = loanService.findAllDto();
        }

        if (loans == null || loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity(loans, HttpStatus.OK);
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

        loanDto = loanService.saveDto(loanDto);
        return new ResponseEntity<>(loanDto, HttpStatus.CREATED);
    }

    /**
     * Patch response entity.
     *
     * @param jsonNodeLoan the json node loan
     * @param id           the id
     * @return the response entity
     * @throws IOException        the io exception
     * @throws JsonPatchException the json patch exception
     */
    @RequestMapping(value = "/loans/{id}", method = RequestMethod.PATCH, consumes = "application/json", produces = {
            "application/json", "application/json-patch+json"})
    public ResponseEntity patch(@RequestBody JsonNode jsonNodeLoan, @PathVariable Integer id){

        Loan loan = loanService.findOne(id);
        if (loan == null) {
            return new ResponseEntity<>("This loan do not exist", HttpStatus.NO_CONTENT);
        } else {
            try {
                loanService.patchLoan(jsonNodeLoan, loan);
            } catch (final Exception e) {
                LOGGER.error("JsonPatch Error" + e);
                return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
