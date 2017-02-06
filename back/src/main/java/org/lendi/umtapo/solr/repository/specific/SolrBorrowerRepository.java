package org.lendi.umtapo.solr.repository.specific;

import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.SolrRepository;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Page<BorrowerDocument> searchByName(String term, Pageable pageable) throws SolrRepositoryException;
}
