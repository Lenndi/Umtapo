package org.lenndi.umtapo.service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Logger;
import org.lenndi.umtapo.controller.Z3950WebService;
import org.lenndi.umtapo.entity.configuration.Z3950;
import org.lenndi.umtapo.entity.configuration.Z3950Configuration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Z39.50 service implementation.
 */
@Service
public class Z3950ServiceImpl implements Z3950Service {
    private static final Logger LOGGER = Logger.getLogger(Z3950WebService.class);

    private Map<Integer, Z3950> providers;
    private File configurationFile;

    /**
     * Instantiates a new Z 3950 service.
     */
    public Z3950ServiceImpl() {
        this.configurationFile = new File("conf/z39-50.yml");
        this.loadProviders();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Z3950> findAll() {
        return this.providers;
    }

    /**
     * {@inheritDoc}
     */
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

        } catch (final IOException e) {
            LOGGER.fatal(e.getMessage());
        }
    }
}
