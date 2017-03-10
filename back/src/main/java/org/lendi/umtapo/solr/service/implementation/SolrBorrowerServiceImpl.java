package org.lendi.umtapo.solr.service.implementation;

import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.mapper.BorrowerMapper;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.SolrBorrowerRepository;
import org.lendi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Borrower index service.
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
    public void saveToIndex(Borrower borrower) {
        BorrowerDocument document = this.borrowerMapper.mapBorrowerToBorrowerDocument(borrower);
        this.documentRepository.save(document);
    }

    @Override
    public void saveToIndex(BorrowerDocument borrowerDocument) {
        this.documentRepository.save(borrowerDocument);
    }

    @Override
    public BorrowerDocument findById(String id) {
        return this.documentRepository.findById(id);
    }

    @Override
    public Page<BorrowerDocument> searchByNameOrEmail(String nameOrEmail, Pageable page) {
        return this.documentRepository.findByNameContainingOrEmailContaining(nameOrEmail, nameOrEmail, page);
    }

    @Override
    public Page<BorrowerDocument> fullSearch(
            String name,
            String email,
            String city,
            String id,
            Pageable page) {
        return this.documentRepository.fullSearch(
                name, email, city, id, page);
    }

    @Override
    public Page<BorrowerDocument> searchAll(Pageable pageable) {
        Page<BorrowerDocument> borrowers;
        borrowers = this.documentRepository.findAll(pageable);

        return borrowers;
    }

    @Override
    public void deleteFromIndex(Integer id) {
        documentRepository.delete(id.toString());
    }
}
