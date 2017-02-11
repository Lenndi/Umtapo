package org.lendi.umtapo.solr.repository.specific;

import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.ParentSolrRepository;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface Borrower document repository.
 */
public interface SolrBorrowerRepository extends ParentSolrRepository<BorrowerDocument> {


    /**
     * Search borrowers by name.
     *
     * @param term     the term
     * @param pageable the pageable
     * @return the page
     * @throws SolrRepositoryException the solr repository exception
     */
    Page<BorrowerDocument> searchByName(String term, Pageable pageable) throws SolrRepositoryException;
}
