package org.lendi.umtapo.solr.repository;


import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * Solr borrower repository.
 */
public interface SolrBorrowerRepository extends SolrCrudRepository<BorrowerDocument, String> {

    /**
     * Find by id borrower document.
     *
     * @param id the id
     * @return the borrower document
     */
    BorrowerDocument findById(String id);

    /**
     * Find by name.
     *
     * @param name  borrower name
     * @param email borrower email
     * @param page  the page
     * @return the page
     */
    Page<BorrowerDocument> findByNameContainingOrEmailContaining(String name, String email, Pageable page);

    /**
     * Full search page.
     *
     * @param name                the name
     * @param email               the email
     * @param city                the city
     * @param id                  the id
     * @param fromSubscriptionEnd the from subscription end
     * @param toSubscriptionEnd   the to subscription end
     * @param page                the page
     * @return the page
     */
    @Query("name:*?0* AND email:*?1* AND city:*?2* AND id:*?3* AND subscriptionEnd:[?4 TO ?5]")
    Page<BorrowerDocument> fullSearch(
            String name,
            String email,
            String city,
            String id,
            String fromSubscriptionEnd,
            String toSubscriptionEnd,
            Pageable page);

    /**
     * Find all borrowers.
     * @param page the page
     * @return all borrowers
     */
    @Query(value = "*.*")
    Page<BorrowerDocument> findAll(Pageable page);
}
