package org.lendi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lendi.umtapo.entity.Z3950;
import org.lendi.umtapo.service.configuration.Z3950Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Z39.50 web service return all Z39.50 access configured in Umtapo.
 */
@RestController
public class Z3950WebService {
    private final static Logger logger = Logger.getLogger(Z3950WebService.class);

    private final Z3950Service z3950Service;

    @Autowired
    public Z3950WebService(Z3950Service z3950Service) {
        Assert.notNull(z3950Service, "Argument z3950Service cannot be null.");
        this.z3950Service = z3950Service;
    }

    @RequestMapping(value = "/z3950", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<Integer, String>> getZ3950() {

        Map<Integer, Z3950> providers = this.z3950Service.findAll();

        if (providers == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Map<Integer, String> providersIndex = new HashMap<>();
        for (Map.Entry<Integer, Z3950> provider : providers.entrySet()) {
            Z3950 z3950 = provider.getValue();
            providersIndex.put(provider.getKey(), z3950.getName());
        }

        return new ResponseEntity<>(providersIndex, HttpStatus.OK);
    }

    @RequestMapping(value = "/z3950/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Z3950> getZ3950(@PathVariable Integer id) {

        Z3950 z3950 = this.z3950Service.find(id);
        if (z3950 == null) {
            logger.info("Z39.50 provider with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(z3950, HttpStatus.OK);
    }
}