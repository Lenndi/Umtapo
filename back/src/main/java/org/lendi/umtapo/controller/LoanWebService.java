package org.lendi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.LoanDto;
import org.lendi.umtapo.service.specific.LoanService;
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
 * Loan WebService.
 *
 * Created by axel on 22/01/17.
 */
@RestController
public class LoanWebService {

    private static final Logger LOGGER = Logger.getLogger(LoanWebService.class);

    private final LoanService loanService;

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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(loanDto, HttpStatus.OK);
    }

    /**
     * Get loans.
     *
     * @return the loans
     */
    @RequestMapping(value = "/loans", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getLoans() {

        List<LoanDto> loanDtos = loanService.findAllDto();

        if (loanDtos == null || loanDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity(loanDtos, HttpStatus.OK);
    }

    /**
     * Sets loan.
     * Validators.required
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
}
