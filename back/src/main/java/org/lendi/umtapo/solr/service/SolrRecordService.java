package org.lendi.umtapo.solr.service;

import org.lendi.umtapo.solr.document.record.RecordDocument;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Solr record service.
 */
public interface SolrRecordService {

    /**
     * Add to index.
     *
     * @param recordDocument the record document
     * @throws SolrRepositoryException the solr repository exception
     */
    void addToIndex(RecordDocument recordDocument) throws SolrRepositoryException;

    /**
     * Delete from index int.
     *
     * @param id the id
     * @return the int
     * @throws SolrRepositoryException the solr repository exception
     */
    int deleteFromIndex(String id) throws SolrRepositoryException;

    /**
     * Search by serial number list.
     *
     * @param serialNumber the serial number
     * @param serialType   the serial type
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    List<RecordDocument> searchBySerialNumber(String serialNumber, String serialType) throws SolrRepositoryException;

    /**
     * Search by title page.
     *
     * @param title    the title
     * @param pageable the pageable
     * @return the page
     * @throws SolrRepositoryException the solr repository exception
     */
    Page<RecordDocument> searchByTitle(String title, Pageable pageable) throws SolrRepositoryException;
}
