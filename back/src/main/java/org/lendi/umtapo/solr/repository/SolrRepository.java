package org.lendi.umtapo.solr.repository;

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
     * Delete.
     *
     * @param id the id
     * @throws SolrRepositoryException the solr repository exception
     */
    void delete(String id) throws SolrRepositoryException;
}
