package org.lendi.umtapo.solr.service.implementation;

import org.lendi.umtapo.solr.document.record.RecordDocument;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
import org.lendi.umtapo.solr.repository.specific.SolrRecordRepository;
import org.lendi.umtapo.solr.service.SolrRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * The type Solr record service.
 */
@Service
public class SolrRecordServiceImpl implements SolrRecordService {

    private final SolrRecordRepository recordRepository;

    /**
     * Instantiates a new Solr record service.
     *
     * @param recordRepository the record repository
     */
    @Autowired
    public SolrRecordServiceImpl(SolrRecordRepository recordRepository) {
        Assert.notNull(recordRepository);

        this.recordRepository = recordRepository;
    }

    @Override
    public void addToIndex(RecordDocument recordDocument) throws SolrRepositoryException {
        this.recordRepository.save(recordDocument);
    }

    @Override
    public int deleteFromIndex(String id) throws SolrRepositoryException {
        return this.recordRepository.delete(id);
    }

    @Override
    public List<RecordDocument> searchBySerialNumber(String serialNumber, String serialType)
            throws SolrRepositoryException {
        return this.recordRepository.searchBySerialNumber(serialNumber, serialType);
    }

    @Override
    public Page<RecordDocument> searchByTitle(String title, Pageable pageable) throws SolrRepositoryException {
        return this.recordRepository.searchByTitle(title, pageable);
    }
}
