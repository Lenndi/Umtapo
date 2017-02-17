package org.lendi.umtapo.solr.service;

import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Solr borrwer service interface.
 */
public interface SolrBorrowerService {

    /**
     * Add to index.
     *
     * @param borrower the borrower
     */
    void addToIndex(Borrower borrower);

    /**
     * Delete from index.
     *
     * @param id the id
     */
    void deleteFromIndex(Integer id);

    /**
     * Search list.
     *
     * @param name     the term
     * @param pageable the pageable
     * @return the list
     */
    Page<BorrowerDocument> fullSearch(String name, String email, String city, Pageable pageable);

    /**
     * Search all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<BorrowerDocument> searchAll(Pageable pageable);
}
