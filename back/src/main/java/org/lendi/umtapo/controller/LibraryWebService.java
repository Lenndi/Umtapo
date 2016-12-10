package org.lendi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.LibraryDto;
import org.lendi.umtapo.service.specific.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Library web service.
 */
@RestController
@CrossOrigin
public class LibraryWebService {

    private final static Logger logger = Logger.getLogger(LibraryWebService.class);

    private final LibraryService libraryService;

    @Autowired
    public LibraryWebService(LibraryService libraryService) {
        Assert.notNull(libraryService, "Argument libraryService cannot be null.");
        this.libraryService = libraryService;
    }

    @RequestMapping(value = "/libraries/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LibraryDto> getLibrary(@PathVariable Integer id) {

        LibraryDto libraryDto = this.libraryService.find(id, true);
        if (libraryDto == null) {
            logger.info("Library with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(libraryDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/libraries", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<LibraryDto>> getLibraries() {

        List<LibraryDto> librariesDto = this.libraryService.findAll(true);
        if (librariesDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(librariesDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/libraries", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LibraryDto> setLibrary(@RequestBody LibraryDto libraryDto) {

        libraryDto = libraryService.save(libraryDto);
        return new ResponseEntity<>(libraryDto, HttpStatus.CREATED);
    }
}