package org.lendi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Borrower service.
 * <p>
 * Created by axel on 05/12/16.
 */
@RestController
public class BorrowerWebService {

    private final static Logger logger = Logger.getLogger(BorrowerWebService.class);

    private final BorrowerService borrowerService;

    @Autowired
    public BorrowerWebService(BorrowerService borrowerService) {
        Assert.notNull(borrowerService);
        this.borrowerService = borrowerService;
    }

    @RequestMapping(value = "/borrowers/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BorrowerDto> getBorrower(@PathVariable Integer id) {

        BorrowerDto borrowerDto = borrowerService.findOneDto(id);
        if (borrowerDto == null) {
            logger.info("Borrower with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(borrowerDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/borrowers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<BorrowerDto>> getBorrowers() {

        List<BorrowerDto> borrowerDtos = borrowerService.findAllDto();
        if (borrowerDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(borrowerDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/borrowers", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BorrowerDto> setUser(@RequestBody BorrowerDto borrowerDto) {

        borrowerDto = borrowerService.saveDto(borrowerDto);
        return new ResponseEntity<>(borrowerDto, HttpStatus.CREATED);
    }
}
