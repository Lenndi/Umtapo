package org.lendi.umtapo.solr.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import javax.annotation.Resource;
import java.net.MalformedURLException;

/**
 * Embedded Solr configuration.
 */
@Configuration
@EnableSolrRepositories(basePackages = {"org.lendi.umtapo.solr.repository"}, multicoreSupport = true)
public class SolrConfig {

    @Resource
    private Environment env;

    /**
     * Solr client solr client.
     *
     * @return the solr client
     * @throws MalformedURLException the malformed url exception
     * @throws IllegalStateException the illegal state exception
     */
    @Bean
    public SolrClient solrClient() throws MalformedURLException, IllegalStateException {
        return new HttpSolrClient(env.getRequiredProperty("solr.url"));
    }
}
