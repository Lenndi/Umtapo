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
     * Find by name.
     *
     * @param name the name
     * @param page the page
     * @return the page
     */
    Page<BorrowerDocument> findByNameContaining(String name, Pageable page);

    /**
     * Find all borrowers.
     * @param page the page
     * @return all borrowers
     */
    @Query(value = "*.*")
    Page<BorrowerDocument> findAll(Pageable page);
}