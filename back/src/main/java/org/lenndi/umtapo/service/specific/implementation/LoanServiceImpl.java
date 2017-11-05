package org.lenndi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import org.lenndi.umtapo.dao.LoanDao;
import org.lenndi.umtapo.dto.LoanDto;
import org.lenndi.umtapo.dto.SimpleLoanDto;
import org.lenndi.umtapo.entity.Loan;
import org.lenndi.umtapo.mapper.LoanMapper;
import org.lenndi.umtapo.service.generic.AbstractGenericService;
import org.lenndi.umtapo.service.specific.ItemService;
import org.lenndi.umtapo.service.specific.LoanService;
import org.lenndi.umtapo.solr.document.BorrowerDocument;
import org.lenndi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * LoanService Implementation.
 * <p>
 * Created by axel on 22/01/17.
 */
@Service
public class LoanServiceImpl extends AbstractGenericService<Loan, Integer> implements LoanService {

    private final LoanMapper loanMapper;
    private final LoanDao loanDao;
    private final SolrBorrowerService solrBorrowerService;
    private final ItemService itemService;

    /**
     * Instantiates a new Loan service.
     *
     * @param loanMapper          the loan mapper
     * @param loanDao             the loan dao
     * @param solrBorrowerService BorrowerDocument repository
     * @param itemService         the item service
     */
    @Autowired
    public LoanServiceImpl(
            LoanMapper loanMapper,
            LoanDao loanDao,
            SolrBorrowerService solrBorrowerService,
            ItemService itemService
    ) {
        this.loanDao = loanDao;
        this.loanMapper = loanMapper;
        this.solrBorrowerService = solrBorrowerService;
        this.itemService = itemService;
    }

    @Override
    @Transactional
    public Loan save(Loan entity) {
        Loan loan = super.save(entity);

        return this.saveToIndex(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanDto saveDto(LoanDto loanDto) {

        return this.loanMapper.mapLoanToLoanDto(this.saveLoanDtoToLoan(loanDto));
    }

    @Override
    public Loan saveLoanDtoToLoan(LoanDto loanDto) {
        Loan loan = this.loanMapper.mapLoanDtoToLoan(loanDto);

        return this.save(loan);
    }

    @Override
    public LoanDto saveLoanToLoanDto(Loan loan) {
        loan = this.save(loan);

        return this.loanMapper.mapLoanToLoanDto(loan);
    }

    @Override
    public Loan findOne(Integer integer) {
        Loan loan = super.findOne(integer);
        loan.setItem(this.itemService.linkRecord(loan.getItem()));

        return loan;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanDto findOneDto(Integer id) {
        Loan loan = this.findOne(id);

        return loanMapper.mapLoanToLoanDto(loan);
    }

    @Override
    public List<Loan> findAll() {
        List<Loan> loans = super.findAll();
        loans.forEach(loan -> loan.setItem(this.itemService.linkRecord(loan.getItem())));

        return loans;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LoanDto> findAllDto() {
        List<Loan> loans = this.findAll();

        return this.mapLoansToLoanDtos(loans);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SimpleLoanDto> findAllDtoByBorrowerIdAndNotReturned(Integer id) {
        List<Loan> loans = loanDao.findByBorrowerIdAndReturnedFalse(id);
        loans.forEach(loan -> loan.setItem(this.itemService.linkRecord(loan.getItem())));

        return this.mapLoansToSimpleLoanDtos(loans);
    }

    /**
     * {@inheritDoc}
     */
    public Integer saveEnd(LoanDto loanDto) {
        return loanDao.setEndById(loanDto.getEnd(), loanDto.getId());
    }

    /**
     * {@inheritDoc}
     */
    public LoanDto patchLoan(JsonNode jsonNodeLoan, Loan loan) throws IllegalAccessException {

        loanMapper.mergeLoanAndJsonNode(loan, jsonNodeLoan);
        this.save(loan);
        return loanMapper.mapLoanToLoanDto(loan);
    }

    private Loan saveToIndex(Loan loan) {
        Integer borrowerId = loan.getBorrower().getId();
        BorrowerDocument borrowerDocument = this.solrBorrowerService.findById(borrowerId.toString());
        borrowerDocument.setNbLoans(this.findAllDtoByBorrowerIdAndNotReturned(borrowerId).size());

        if (borrowerDocument.getNbLoans() > borrowerDocument.getQuota()) {
            borrowerDocument.setTooMuchLoans(true);
        } else {
            borrowerDocument.setTooMuchLoans(false);
        }

        Loan olderLoanToReturn = this.loanDao.findFirstByBorrowerIdAndReturnedFalseOrderByEndAsc(borrowerId);
        if (olderLoanToReturn != null) {
            borrowerDocument.setOlderReturn(Date.from(olderLoanToReturn.getEnd().toInstant()));
        }
        this.solrBorrowerService.saveToIndex(borrowerDocument);

        return loan;
    }

    private List<LoanDto> mapLoansToLoanDtos(List<Loan> loans) {

        List<LoanDto> loanDtos = new ArrayList<>();
        loans.forEach(loan -> loanDtos.add(loanMapper.mapLoanToLoanDto(loan)));

        return loanDtos;
    }

    private List<SimpleLoanDto> mapLoansToSimpleLoanDtos(List<Loan> loans) {

        List<SimpleLoanDto> simpleLoanDtos = new ArrayList<>();
        loans.forEach(loan -> simpleLoanDtos.add(loanMapper.mapLoanToSimpleLoanDto(loan)));

        return simpleLoanDtos;
    }
}
