package org.lendi.umtapo.solr.service;

import org.lendi.umtapo.solr.document.bean.record.Record;
import org.lendi.umtapo.solr.exception.InvalidRecordException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Solr record service.
 */
public interface SolrRecordService {

    /**
     * Find by id record.
     *
     * @param id the id
     * @return the record
     */
    Record findById(String id);

    /**
     * Add to index.
     *
     * @param record the record document
     * @return the record
     * @throws InvalidRecordException the invalid record exception
     */
    Record save(Record record) throws InvalidRecordException;

    /**
     * Delete from index int.
     *
     * @param id the id
     */
    void delete(String id);

    /**
     * Search by serial number list.
     *
     * @param serialNumber the serial number
     * @param serialType   the serial type
     * @return the list
     */
    List<Record> searchBySerialNumber(String serialNumber, String serialType);

    /**
     * Search by title page.
     *
     * @param title    the title
     * @param pageable the pageable
     * @return the page
     */
    Page<Record> searchByTitle(String title, Pageable pageable);
}
