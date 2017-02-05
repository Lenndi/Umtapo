package org.lendi.umtapo.solr.repository.specific;

import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.SolrRepository;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;

import java.util.List;

/**
 * The interface Borrower document repository.
 */
public interface SolrBorrowerRepository extends SolrRepository<BorrowerDocument> {

    /**
     * Search by name list.
     *
     * @param term the term
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    List<BorrowerDocument> searchByName(String term) throws SolrRepositoryException;

    /**
     * Search all list.
     *
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    List<BorrowerDocument> searchAll() throws SolrRepositoryException;
}
