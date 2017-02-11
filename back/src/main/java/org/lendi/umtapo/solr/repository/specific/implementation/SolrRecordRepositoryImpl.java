package org.lendi.umtapo.solr.repository.specific.implementation;

import org.apache.solr.client.solrj.SolrQuery;
import org.lendi.umtapo.solr.configuration.SolrConfig;
import org.lendi.umtapo.solr.document.record.RecordDocument;
import org.lendi.umtapo.solr.repository.AbstractParentSolrRepository;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
import org.lendi.umtapo.solr.repository.specific.SolrRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Solr record repository.
 */
@Service
public class SolrRecordRepositoryImpl
        extends AbstractParentSolrRepository<RecordDocument> implements SolrRecordRepository {

    /**
     * Instantiates a new Solr record repository.
     *
     * @param solrConfig the solr config
     */
    @Autowired
    public SolrRecordRepositoryImpl(SolrConfig solrConfig) {
        super(solrConfig, "record");
    }

    @Override
    public List<RecordDocument> searchBySerialNumber(String serialNumber, String serialType)
            throws SolrRepositoryException {
        String query = String.format("serialNumber:%s AND serialType:%s", serialNumber, serialType);
        SolrQuery solrQuery = this.queryChildrenWithParent(query);

        return this.getList(solrQuery);
    }

    @Override
    public Page<RecordDocument> searchByTitle(String title, Pageable pageable) throws SolrRepositoryException {
        SolrQuery solrQuery = this.queryChildrenWithParent("title:" + title);

        return this.getPage(solrQuery, pageable);
    }
}
