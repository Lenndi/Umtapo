package org.lenndi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lenndi.umtapo.dto.LibraryDto;
import org.lenndi.umtapo.service.specific.LibraryService;
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

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Library web service.
 */
@RestController
@CrossOrigin
public class LibraryWebService {

    private static final Logger LOGGER = Logger.getLogger(LibraryWebService.class);

    private final LibraryService libraryService;

    /**
     * Instantiates a new Library web service.
     *
     * @param libraryService the library service
     */
    @Autowired
    public LibraryWebService(LibraryService libraryService) {
        Assert.notNull(libraryService);

        this.libraryService = libraryService;
    }

    /**
     * Gets library.
     *
     * @param id the id
     * @return the library
     */
    @RequestMapping(value = "/libraries/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<LibraryDto> getLibrary(@PathVariable Integer id) {

        LibraryDto libraryDto = this.libraryService.findOneDto(id);
        if (libraryDto == null) {
            LOGGER.info("Library with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(libraryDto, HttpStatus.OK);
    }

    /**
     * Gets libraries.
     *
     * @param external the external
     * @return the libraries
     */
    @RequestMapping(value = "/libraries", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<LibraryDto>> getLibraries(@PathParam("external") Boolean external) {

        List<LibraryDto> librariesDto;

        if (external) {
            librariesDto = this.libraryService.findAllExternal();
        } else {
            librariesDto = this.libraryService.findAllPartner();
        }

        if (librariesDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(librariesDto, HttpStatus.OK);
    }

    /**
     * Create a partner library.
     *
     * @param libraryDto the library dto
     * @return the library
     */
    @RequestMapping(value = "/libraries/partner",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LibraryDto> setPartnerLibrary(@RequestBody LibraryDto libraryDto) {

        libraryDto.setExternal(false);
        libraryDto = libraryService.saveDto(libraryDto);

        return new ResponseEntity<>(libraryDto, HttpStatus.CREATED);
    }

    /**
     * Create an external library.
     *
     * @param libraryDto the library dto
     * @return the library
     */
    @RequestMapping(value = "/libraries/external",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LibraryDto> setExternalLibrary(@RequestBody LibraryDto libraryDto) {

        libraryDto.setExternal(true);
        libraryDto = libraryService.saveDto(libraryDto);

        return new ResponseEntity<>(libraryDto, HttpStatus.CREATED);
    }
}
