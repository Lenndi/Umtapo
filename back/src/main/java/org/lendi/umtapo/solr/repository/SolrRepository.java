package org.lendi.umtapo.solr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface Solr repository.
 *
 * @param <T> the type parameter
 */
public interface SolrRepository<T> {

    /**
     * Save.
     *
     * @param document the document
     * @throws SolrRepositoryException the solr repository exception
     */
    void save(T document) throws SolrRepositoryException;


    /**
     * Search all page.
     *
     * @param pageable the pageable
     * @return the page
     * @throws SolrRepositoryException the solr repository exception
     */
    Page<T> searchAll(Pageable pageable) throws SolrRepositoryException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws SolrRepositoryException the solr repository exception
     */
    void delete(String id) throws SolrRepositoryException;
}
