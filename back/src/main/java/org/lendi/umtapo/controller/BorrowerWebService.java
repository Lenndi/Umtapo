package org.lendi.umtapo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.lendi.umtapo.configuration.Profile;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Borrower service.
 * <p>
 * Created by axel on 05/12/16.
 */
@RestController
@CrossOrigin
public class BorrowerWebService {

    private static final Logger LOGGER = Logger.getLogger(BorrowerWebService.class);

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
    public ResponseEntity<BorrowerDto> getBorrower(@PathVariable Integer id) {

        BorrowerDto borrowerDto = borrowerService.findOneDto(id);
        if (borrowerDto == null) {
            LOGGER.info("Borrower with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(borrowerDto, HttpStatus.OK);
    }


    /**
     * Gets borrowers.
     *
     * @param page the page
     * @param size the size
     * @return the borrowers
     */
    @RequestMapping(value = "/borrowersPagineable", method = RequestMethod.GET, produces = {MediaType
            .APPLICATION_JSON_VALUE})
    public ResponseEntity<Page> getBorrowers(@RequestParam int page, @RequestParam int size) {

        Pageable pageable = new PageRequest(page, size, new Sort("id"));
        Page<BorrowerDto> borrowerDtos = borrowerService.findAllPageableDto(pageable);

        if (borrowerDtos == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(borrowerDtos, HttpStatus.OK);
    }

    /**
     * Gets borrowers.
     *
     * @return the borrowers
     */
    @RequestMapping(value = "/borrowers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getBorrowers(@RequestParam(required = false) String view) {

        List<BorrowerDto> borrowerDtos = borrowerService.findAllDto();

        if (borrowerDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
        }

        if (view != null) {
            MappingJacksonValue results = dynamicMapping(view, borrowerDtos);
            if (results == null) {
                return new ResponseEntity("The view does not exist", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(results, HttpStatus.OK);
        }

        return new ResponseEntity(borrowerDtos, HttpStatus.OK);
    }

    /**
     * Sets borrower.
     * Validators.required
     *
     * @param borrowerDto the borrower dto
     * @return the borrower
     */
    @RequestMapping(value = "/borrowers", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BorrowerDto> setBorrower(@RequestBody BorrowerDto borrowerDto) {

        borrowerDto = borrowerService.saveDto(borrowerDto);
        return new ResponseEntity<>(borrowerDto, HttpStatus.CREATED);
    }

    private MappingJacksonValue dynamicMapping(String jsonView, Object object) {

        MappingJacksonValue wrapper = new MappingJacksonValue(object);

        if (jsonView.equals("BorrowerSearchView")) {
            wrapper.setSerializationView(Profile.BorrowerSearchView.class);
        } else {
            wrapper = null;
        }
        return wrapper;
    }
}
