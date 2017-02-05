package org.lendi.umtapo.solr.repository.specific.implementation;

import org.lendi.umtapo.solr.configuration.SolrConfig;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.AbstractSolrRepository;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
import org.lendi.umtapo.solr.repository.specific.SolrBorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Solr borrower repository.
 */
@Service
public class SolrBorrowerRepositoryImpl extends AbstractSolrRepository<BorrowerDocument> implements SolrBorrowerRepository {

    /**
     * Instantiates a new Solr borrower repository.
     *
     * @param solrConfig the solr config
     */
    @Autowired
    public SolrBorrowerRepositoryImpl(SolrConfig solrConfig) {
        super(solrConfig, "borrower");
    }

    @Override
    public List<BorrowerDocument> searchByName(String term) throws SolrRepositoryException {
        return this.queryParentWithChildren(String.format("name:%s", term));
    }

    @Override
    public List<BorrowerDocument> searchAll() throws SolrRepositoryException {
        return this.queryParentWithChildren("*:*");
    }
}
