package org.lenndi.umtapo.solr.service.implementation;

import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.mapper.BorrowerMapper;
import org.lenndi.umtapo.solr.document.BorrowerDocument;
import org.lenndi.umtapo.solr.repository.SolrBorrowerRepository;
import org.lenndi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            Boolean tooMuchLoans,
            Boolean lateness,
            Pageable page) {

        String tooMuchLoansStr = "*";
        if (tooMuchLoans != null) {
            tooMuchLoansStr = tooMuchLoans.toString();
        }

        String olderReturnFrom = "*";
        String olderReturnTo = "*";
        if (lateness != null) {
            if (lateness) {
                olderReturnTo = "NOW";
            } else {
                olderReturnFrom = "NOW";
            }
        }

        return this.documentRepository.fullSearch(
                name, email, city, id, tooMuchLoansStr, olderReturnFrom, olderReturnTo, page);
    }

    @Override
    public Page<BorrowerDocument> searchAll(Pageable pageable) {
        Page<BorrowerDocument> borrowers;
        borrowers = this.documentRepository.findAll(pageable);

        return borrowers;
    }

    @Override
    // TODO: Implements PartialChange when issue https://jira.spring.io/browse/DATASOLR-383 will be resolved
    public void updateToIndex(Borrower borrower) {
        BorrowerDocument document = this.borrowerMapper.mapBorrowerToBorrowerDocument(borrower);
        BorrowerDocument update = this.documentRepository.findById(document.getId());

        update.setName(document.getName());
        update.setComment(document.getComment());
        update.setBirthday(document.getBirthday());
        update.setQuota(document.getQuota());
        update.setEmailOptin(document.getEmailOptin());
        update.setAddress1(document.getAddress1());
        update.setAddress2(document.getAddress2());
        update.setZip(document.getZip());
        update.setCity(document.getCity());
        update.setPhone(document.getPhone());
        update.setEmail(document.getEmail());

        this.documentRepository.save(update);
    }

    @Override
    public void deleteFromIndex(Integer id) {
        documentRepository.delete(id.toString());
    }
}
