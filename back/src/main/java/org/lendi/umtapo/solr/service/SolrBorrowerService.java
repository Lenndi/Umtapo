package org.lendi.umtapo.solr.service;

import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface Borrower index service.
 */
public interface SolrBorrowerService {


    /**
     * Add to index.
     *
     * @param borrower the borrower
     * @throws SolrRepositoryException the solr repository exception
     */
    void addToIndex(Borrower borrower) throws SolrRepositoryException;

    /**
     * Delete from index.
     *
     * @param id the id
     * @return the int
     * @throws SolrRepositoryException the solr repository exception
     */
    int deleteFromIndex(Integer id) throws SolrRepositoryException;

    /**
     * Search list.
     *
     * @param term     the term
     * @param pageable the pageable
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    Page<BorrowerDocument> searchByName(String term, Pageable pageable) throws SolrRepositoryException;


    /**
     * Search all page.
     *
     * @param pageable the pageable
     * @return the page
     * @throws SolrRepositoryException the solr repository exception
     */
    Page<BorrowerDocument> searchAll(Pageable pageable) throws SolrRepositoryException;
}
