package org.lendi.umtapo.solr.service.implementation;

import org.apache.log4j.Logger;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.mapper.BorrowerMapper;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
import org.lendi.umtapo.solr.repository.specific.SolrBorrowerRepository;
import org.lendi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * The type Borrower index service.
 */
@Service
public class SolrBorrowerServiceImpl implements SolrBorrowerService {

    private static final Logger LOGGER = Logger.getLogger(SolrBorrowerServiceImpl.class);

    private final SolrBorrowerRepository documentRepository;
    private final BorrowerMapper borrowerMapper;

    /**
     * Instantiates a new Borrower index service.
     *
     * @param documentRepository the document repository
     * @param borrowerMapper     the borrower mapper
     */
    @Autowired
    public SolrBorrowerServiceImpl(SolrBorrowerRepository documentRepository, BorrowerMapper borrowerMapper) {
        Assert.notNull(documentRepository);
        Assert.notNull(borrowerMapper);

        this.documentRepository = documentRepository;
        this.borrowerMapper = borrowerMapper;
    }

    @Override
    public void addToIndex(Borrower borrower) {
        BorrowerDocument document = this.mapBorrowerToBorrowerDocument(borrower);

        try {
            documentRepository.save(document);
        } catch (SolrRepositoryException e) {
            LOGGER.error(e.getStackTrace());
        }
    }

    @Override
    public Page<BorrowerDocument> searchByName(String term, Pageable pageable) {
        Page<BorrowerDocument> borrowers = null;
        try {
            borrowers = this.documentRepository.searchByName(term, pageable);
        } catch (SolrRepositoryException e) {
            LOGGER.error(e.getStackTrace());
        }

        return borrowers;
    }

    @Override
    public Page<BorrowerDocument> searchAll(Pageable pageable) {
        Page<BorrowerDocument> borrowers = null;
        try {
            borrowers = this.documentRepository.searchAll(pageable);
        } catch (SolrRepositoryException e) {
            LOGGER.error(e.getStackTrace());
        }

        return borrowers;
    }

    @Override
    public void deleteFromIndex(Integer id) {
        try {
            documentRepository.delete(id.toString());
        } catch (SolrRepositoryException e) {
            LOGGER.error(e.getStackTrace());
        }
    }

    private BorrowerDocument mapBorrowerToBorrowerDocument(Borrower borrower) {
        return this.borrowerMapper.mapBorrowerToBorrowerDocument(borrower);
    }
}
