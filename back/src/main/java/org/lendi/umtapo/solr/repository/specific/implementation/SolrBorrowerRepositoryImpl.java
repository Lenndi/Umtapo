package org.lendi.umtapo.solr.repository.specific.implementation;

import org.apache.solr.client.solrj.SolrServerException;
import org.lendi.umtapo.solr.configuration.SolrConfig;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.AbstractSolrRepository;
import org.lendi.umtapo.solr.repository.specific.SolrBorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SolrBorrowerRepositoryImpl extends AbstractSolrRepository<BorrowerDocument> implements SolrBorrowerRepository {

    @Autowired
    public SolrBorrowerRepositoryImpl(SolrConfig solrConfig) throws SolrServerException {
        super(solrConfig, "borrower");
    }

    @Override
    public List<BorrowerDocument> findByEmailOrName(String term) throws IOException, SolrServerException {
        return this.queryWithChild(String.format("name:%s OR email:%s", term, term));
    }
}
