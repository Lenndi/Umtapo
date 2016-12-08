package org.lendi.umtapo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Logger;
import org.lendi.umtapo.entity.Z3950;
import org.lendi.umtapo.entity.Z3950Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Z39.50 web service return all Z39.50 access configured in Umtapo.
 */
@RestController
public class Z3950WebService {

    private final static Logger logger = Logger.getLogger(Z3950WebService.class);


    @RequestMapping(value = "/z3950", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<Integer, String>> getZ3950() {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<Integer, String> providers = new HashMap<>();
        File file = new File("src/main/resources/z39-50.yml");
        try {
            Z3950Configuration z3950Providers = mapper.readValue(file, Z3950Configuration.class);

            for (Z3950 provider : z3950Providers.getProviders()) {
                providers.put(provider.getId(), provider.getName());
            }

            return new ResponseEntity<>(providers, HttpStatus.CONFLICT);
        } catch (IOException e) {
            logger.fatal(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    /*@RequestMapping(value = "/libraries", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
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
    }*/
}