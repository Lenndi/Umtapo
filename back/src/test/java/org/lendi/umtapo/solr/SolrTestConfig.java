package org.lendi.umtapo.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Configuration
@EnableSolrRepositories(basePackages = {"org.lendi.umtapo.solr.repository"}, multicoreSupport = true)
public class SolrTestConfig {

    @Bean
    public SolrClient solrClient()
            throws IOException, IllegalStateException, SAXException, ParserConfigurationException {
        EmbeddedSolrServerFactory solrServerFactory = new EmbeddedSolrServerFactory("solr");

        return solrServerFactory.getSolrClient();
    }

    @Bean
    public SolrTemplate solrTemplate() throws ParserConfigurationException, SAXException, IOException {
        return new SolrTemplate(solrClient());
    }
}
