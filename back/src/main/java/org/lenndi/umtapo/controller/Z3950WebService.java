package org.lenndi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lenndi.umtapo.entity.configuration.Z3950;
import org.lenndi.umtapo.entity.configuration.Z3950Description;
import org.lenndi.umtapo.service.configuration.Z3950Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Z39.50 web service return all Z39.50 access configured in Umtapo.
 */
@RestController
@CrossOrigin
public class Z3950WebService {
    private static final Logger LOGGER = Logger.getLogger(Z3950WebService.class);

    private final Z3950Service z3950Service;

    /**
     * Instantiates a new Z 3950 web service.
     *
     * @param z3950Service the z 3950 service
     */
    @Autowired
    public Z3950WebService(Z3950Service z3950Service) {
        this.z3950Service = z3950Service;
    }

    /**
     * Gets all z 3950.
     *
     * @return the all z 3950
     */
    @RequestMapping(value = "/z3950", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Z3950Description>> getAllZ3950() {

        Map<Integer, Z3950> providers = this.z3950Service.findAll();

        if (providers == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Z3950Description> providersDescription = new ArrayList<>();
        for (final Map.Entry<Integer, Z3950> provider : providers.entrySet()) {
            Z3950 z3950 = provider.getValue();
            providersDescription.add(new Z3950Description(provider.getKey(), z3950.getName()));
        }

        return new ResponseEntity<>(providersDescription, HttpStatus.OK);
    }

    /**
     * Gets z 3950.
     *
     * @param id the id
     * @return the z 3950
     */
    @RequestMapping(value = "/z3950/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Z3950> getZ3950(@PathVariable Integer id) {

        Z3950 z3950 = this.z3950Service.find(id);
        if (z3950 == null) {
            LOGGER.info("Z39.50 provider with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(z3950, HttpStatus.OK);
    }
}
