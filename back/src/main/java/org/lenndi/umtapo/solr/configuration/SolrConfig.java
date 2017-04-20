package org.lenndi.umtapo.solr.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;

import javax.annotation.Resource;

/**
 * Solr configuration. Set solr.embedded to true in application.properties to use Solr Embedded server.
 */
@Configuration
@EnableSolrRepositories(basePackages = {"org.lenndi.umtapo.solr.repository"}, multicoreSupport = true)
public class SolrConfig {

    @Resource
    private Environment env;

    /**
     * Solr client.
     *
     * @return the solr client, embedded or HTTP
     * @throws Exception exception
     */
    @Bean
    public SolrClient solrClient() throws Exception {
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

    /**
     * Solr template.
     *
     * @return the solr template
     * @throws Exception exception
     */
    @Bean
    public SolrTemplate solrTemplate() throws Exception {
        return new SolrTemplate(solrClient());
    }
}
