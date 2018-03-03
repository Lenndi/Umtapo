package org.lenndi.umtapo.solr.repository;

import org.lenndi.umtapo.solr.document.RecordDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * TSolr record repository.
 */
public interface SolrRecordRepository extends SolrCrudRepository<RecordDocument, String> {

    /**
     * Find record document by id.
     *
     * @param id the id
     * @return the record document
     */
    RecordDocument findById(String id);

    /**
     * Find by serial number record document.
     *
     * @param serialNumber the serial number
     * @return the record document
     */
    RecordDocument findBySerialNumber(String serialNumber);

    /**
     * Find by items record document.
     *
     * @param id the id
     * @return the record document
     */
    RecordDocument findByItems(String id);

    /**
     * Find record documents by serial number and serial type.
     *
     * @param serialNumber the serial number
     * @param serialType   the serial type
     * @return the list
     */
    List<RecordDocument> findBySerialNumberContainingAndSerialType(String serialNumber, String serialType);

    /**
     * Find record documents by main title.
     *
     * @param title the title
     * @param page  the page
     * @return the page
     */
    Page<RecordDocument> findByMainTitleContaining(String title, Pageable page);

    /**
     * Find by main title list.
     *
     * @param title the title
     * @return the list
     */
    @Query("mainTitle:*?0*")
    List<RecordDocument> findByMainTitleContaining(String title);

    /**
     * Find item by serialNumber.
     *
     * @param serialNumber the serial number
     * @param serialType   the serial type
     * @param page         the page
     * @return the page
     */
    Page<RecordDocument> findBySerialNumberContainingAndSerialType(
            String serialNumber,
            String serialType,
            Pageable page
    );

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
    @Query("mainTitle:*?0* AND _name_:*?1* AND editorName:*?2* AND id:?3* AND publicationDate:?4*")
    Page<RecordDocument> fullSearch(
            String title,
            String author,
            String publisher,
            String id,
            String publicationDate,
            Pageable page);
}
