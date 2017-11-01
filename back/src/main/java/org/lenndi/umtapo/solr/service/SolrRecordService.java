package org.lenndi.umtapo.solr.service;

import com.github.ladutsko.isbn.ISBNException;
import org.lenndi.umtapo.solr.document.bean.record.Record;
import org.lenndi.umtapo.solr.exception.InvalidRecordException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Solr record service interface.
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
     * Find by item id record.
     *
     * @param itemId the item id
     * @return the record
     */
    Record findByItemId(Integer itemId);

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


    /**
     * Search by title list.
     *
     * @param title the title
     * @return the list
     */
    List<Record> searchByTitle(String title);

    /**
     * Find item by serialNumber.
     *
     * @param serialNumber the serial number
     * @param serialType   the serial type
     * @param page         the page
     * @return the page
     * @throws ISBNException the isbn exception
     */
    Page<Record> searchBySerialNumberAndSerialType(String serialNumber, String serialType, Pageable page)
            throws ISBNException;

    /**
     * Full search page.
     *
     * @param title           the title
     * @param author          the author
     * @param publisher       the publisher
     * @param id              the id
     * @param publicationDate the publication date
     * @param page            the page
     * @return the page
     */
    Page<Record> fullSearch(
            String title,
            String author,
            String publisher,
            String id,
            String publicationDate,
            Pageable page);
}
