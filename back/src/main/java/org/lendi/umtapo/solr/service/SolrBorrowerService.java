package org.lendi.umtapo.solr.service;

import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.solr.document.BorrowerDocument;

import java.util.List;

/**
 * The interface Borrower index service.
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
     * @param term the term
     * @return the list
     */
    List<BorrowerDocument> searchByNameOrEmail(String term);
}
