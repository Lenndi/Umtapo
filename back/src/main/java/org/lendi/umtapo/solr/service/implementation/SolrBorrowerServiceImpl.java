package org.lendi.umtapo.solr.service.implementation;

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
    public void addToIndex(Borrower borrower) throws SolrRepositoryException {
        BorrowerDocument document = this.mapBorrowerToBorrowerDocument(borrower);
        documentRepository.save(document);
    }

    @Override
    public Page<BorrowerDocument> searchByName(String term, Pageable pageable) throws SolrRepositoryException {
        Page<BorrowerDocument> borrowers = null;
        borrowers = this.documentRepository.searchByName(term, pageable);

        return borrowers;
    }

    @Override
    public Page<BorrowerDocument> searchAll(Pageable pageable) throws SolrRepositoryException {
        Page<BorrowerDocument> borrowers = null;
        borrowers = this.documentRepository.searchAll(pageable);

        return borrowers;
    }

    @Override
    public int deleteFromIndex(Integer id) throws SolrRepositoryException {
        return documentRepository.delete(id.toString());
    }

    private BorrowerDocument mapBorrowerToBorrowerDocument(Borrower borrower) {
        return this.borrowerMapper.mapBorrowerToBorrowerDocument(borrower);
    }
}
