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
    void saveToIndex(Borrower borrower);

    /**
     * Save to index.
     *
     * @param borrowerDocument the borrower document
     */
    void saveToIndex(BorrowerDocument borrowerDocument);

    /**
     * Find by id borrower document.
     *
     * @param id the id
     * @return the borrower document
     */
    BorrowerDocument findById(String id);

    /**
     * Delete from index.
     *
     * @param id the id
     */
    void deleteFromIndex(Integer id);

    /**
     * Search borrower in index by name containing term.
     *
     * @param name term
     * @param page page
     * @return Borrower page
     */
    Page<BorrowerDocument> searchByNameOrEmail(String name, Pageable page);

    /**
     * Search list.
     *
     * @param name         the term
     * @param email        the email
     * @param city         the city
     * @param id           the id
     * @param tooMuchLoans the too much loans
     * @param lateness     the lateness
     * @param pageable     the pageable
     * @return the list
     */
    Page<BorrowerDocument> fullSearch(
            String name,
            String email,
            String city,
            String id,
            Boolean tooMuchLoans,
            Boolean lateness,
            Pageable pageable);

    /**
     * Search all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<BorrowerDocument> searchAll(Pageable pageable);
}
