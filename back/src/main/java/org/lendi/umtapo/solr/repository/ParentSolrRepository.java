package org.lendi.umtapo.solr.repository;

import org.lendi.umtapo.solr.document.ParentDocument;

/**
 * The interface Parent solr repository.
 *
 * @param <T> the type parameter
 */
public interface ParentSolrRepository<T extends ParentDocument> extends SolrRepository<T> {

    /**
     * Delete int.
     *
     * @param id the id
     * @return 200 if ok
     * @throws SolrRepositoryException the solr repository exception
     */
    int delete(String id) throws SolrRepositoryException;
}
