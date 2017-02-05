package org.lendi.umtapo.solr.configuration;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.core.CoreContainer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.file.Path;

import static org.apache.commons.io.FileUtils.getFile;

/**
 * Embedded Solr configuration.
 */
@Component
public class SolrConfig {

    private static final Logger LOGGER = Logger.getLogger(SolrConfig.class);

    @Resource
    private Environment env;

    /**
     * Solr client solr client.
     *
     * @param core the core
     * @return the solr client
     */
    public SolrClient solrClient(String core) {
        SolrClient solrClient;
        final String solrHome = this.env.getProperty("solr.home");
        final String solrUrl = this.env.getProperty("solr.url");

        if (!solrHome.equals("")) {
            final Path solrPath = getFile(solrHome).toPath().toAbsolutePath();
            CoreContainer coreContainer = CoreContainer.createAndLoad(solrPath, solrPath.resolve("solr.xml"));
            solrClient = new EmbeddedSolrServer(coreContainer, core);
        } else {
            if (solrUrl.equals("")) {
                LOGGER.fatal("Can't connect to Solr, "
                        + "both solr.home and solr.url are empty in application.properties !");
            }
            solrClient = new HttpSolrClient.Builder(solrUrl + core).build();
        }

        return solrClient;
    }
}
