package org.lendi.umtapo.service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Logger;
import org.lendi.umtapo.controller.Z3950WebService;
import org.lendi.umtapo.entity.configuration.Z3950;
import org.lendi.umtapo.entity.configuration.Z3950Configuration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Z39.50 service implementation.
 */
@Service
public class Z3950ServiceImpl implements Z3950Service {
    private final static Logger logger = Logger.getLogger(Z3950WebService.class);

    private Map<Integer, Z3950> providers;
    private File configurationFile;

    public Z3950ServiceImpl() {
        this.configurationFile = new File("src/main/resources/z39-50.yml");
        this.loadProviders();
    }

    @Override
    public Map<Integer, Z3950> findAll() {
        return this.providers;
    }

    @Override
    public Z3950 find(int providerId) {
        return this.providers.get(providerId);
    }

    /**
     * Load all Z39.50 providers in configuration file.
     */
    private void loadProviders() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Z3950Configuration z3950Configuration = mapper.readValue(this.configurationFile, Z3950Configuration.class);
            this.providers = z3950Configuration.getProviders();

        } catch (IOException e) {
            logger.fatal(e.getMessage());
        }
    }
}
