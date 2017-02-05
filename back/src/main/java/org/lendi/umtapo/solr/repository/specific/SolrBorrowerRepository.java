package org.lendi.umtapo.solr.repository.specific;

import org.apache.solr.client.solrj.SolrServerException;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.SolrRepository;

import java.io.IOException;
import java.util.List;

/**
 * The interface Borrower document repository.
 */
public interface SolrBorrowerRepository extends SolrRepository<BorrowerDocument> {

    List<BorrowerDocument> findByEmailOrName(String term) throws IOException, SolrServerException;
}
