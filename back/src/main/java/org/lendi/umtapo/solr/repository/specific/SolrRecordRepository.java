package org.lendi.umtapo.solr.repository.specific;


import org.lendi.umtapo.solr.document.record.RecordDocument;
import org.lendi.umtapo.solr.repository.ParentSolrRepository;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Solr record repository.
 */
public interface SolrRecordRepository extends ParentSolrRepository<RecordDocument> {

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
