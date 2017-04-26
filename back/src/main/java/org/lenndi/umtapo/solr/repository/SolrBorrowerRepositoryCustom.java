package org.lenndi.umtapo.solr.repository;


import org.lenndi.umtapo.solr.document.BorrowerDocument;

/**
 * The interface Solr borrower repository custom.
 */
public interface SolrBorrowerRepositoryCustom {

    /**
     * Partial BorrowerDocument update.
     *
     * @param borrowerDocument Borrower to update
     */
    void update(BorrowerDocument borrowerDocument);
}
