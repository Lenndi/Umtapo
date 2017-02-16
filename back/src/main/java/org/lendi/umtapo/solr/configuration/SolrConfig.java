package org.lendi.umtapo.solr.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Solr configuration. Set solr.embedded to true in application.properties to use Solr Embedded server.
 */
@Configuration
@EnableSolrRepositories(basePackages = {"org.lendi.umtapo.solr.repository"}, multicoreSupport = true)
public class SolrConfig {

    @Resource
    private Environment env;

    /**
     * Solr client.
     *
     * @return the solr client, embedded or HTTP
     * @throws IOException                  the io exception
     * @throws IllegalStateException        the illegal state exception
     * @throws SAXException                 the sax exception
     * @throws ParserConfigurationException the parser configuration exception
     */
    @Bean
    public SolrClient solrClient()
            throws IOException, IllegalStateException, SAXException, ParserConfigurationException {
        SolrClient solrClient;

        if (env.getRequiredProperty("solr.embedded").equals("true")) {
            String solrHome = env.getRequiredProperty("solr.home");
            EmbeddedSolrServerFactory solrServerFactory = new EmbeddedSolrServerFactory(solrHome);
            solrClient = solrServerFactory.getSolrClient();
        } else {
            solrClient = new HttpSolrClient(env.getRequiredProperty("solr.url"));
        }

        return solrClient;
    }
}
