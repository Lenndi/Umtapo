package org.lendi.umtapo.solr.repository;

import org.lendi.umtapo.solr.document.RecordDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * The interface Solr record repository.
 */
public interface SolrRecordRepository extends SolrCrudRepository<RecordDocument, String> {

    /**
     * Find by id record document.
     *
     * @param id the id
     * @return the record document
     */
    RecordDocument findById(String id);

    /**
     * Find by serial number and serial type list.
     *
     * @param serialNumber the serial number
     * @param serialType   the serial type
     * @return the list
     */
    List<RecordDocument> findBySerialNumberAndSerialType(String serialNumber, String serialType);

    /**
     * Find by main title page.
     *
     * @param title the title
     * @param page  the page
     * @return the page
     */
    Page<RecordDocument> findByMainTitle(String title, Pageable page);
}
