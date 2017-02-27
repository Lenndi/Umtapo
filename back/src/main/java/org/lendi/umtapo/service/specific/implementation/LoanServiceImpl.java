package org.lendi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import org.lendi.umtapo.dao.LoanDao;
import org.lendi.umtapo.dto.LoanDto;
import org.lendi.umtapo.entity.Loan;
import org.lendi.umtapo.mapper.LoanMapper;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.ItemService;
import org.lendi.umtapo.service.specific.LoanService;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
        Assert.notNull(loanMapper);
        Assert.notNull(loanDao);
        Assert.notNull(solrBorrowerService);
        Assert.notNull(itemService);

        this.loanDao = loanDao;
        this.loanMapper = loanMapper;
        this.solrBorrowerService = solrBorrowerService;
        this.itemService = itemService;
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
        loan = this.save(loan);

        Integer borrowerId = loanDto.getBorrower().getId();
        BorrowerDocument borrowerDocument = this.solrBorrowerService.findById(borrowerId.toString());
        borrowerDocument.setNbLoans(this.findAllDtoByBorrowerIdAndNotReturned(borrowerId).size());

        if (borrowerDocument.getNbLoans() > borrowerDocument.getQuota()) {
            borrowerDocument.setTooMuchLoans(true);
        } else {
            borrowerDocument.setTooMuchLoans(false);
        }

        Loan olderLoanToReturn = this.loanDao.findFirstByBorrowerIdAndReturnedFalseOrderByEndAsc(borrowerId);
        borrowerDocument.setOlderReturn(olderLoanToReturn.getEnd());
        this.solrBorrowerService.saveToIndex(borrowerDocument);

        return loan;
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
    public List<LoanDto> findAllDtoByBorrowerIdAndNotReturned(Integer id) {
        List<Loan> loans = loanDao.findByBorrowerIdAndReturnedFalse(id);
        loans.forEach(loan -> loan.setItem(this.itemService.linkRecord(loan.getItem())));

        return this.mapLoansToLoanDtos(loans);
    }

    /**
     * {@inheritDoc}
     */
    public Integer saveEnd(LoanDto loanDto) {
        return loanDao.saveConditonById(loanDto.getEnd(), loanDto.getId());
    }

    /**
     * {@inheritDoc}
     */
    public LoanDto patchLoan(JsonNode jsonNodeLoan, Loan loan) throws IllegalAccessException {

        loanMapper.mergeLoanAndJsonNode(loan, jsonNodeLoan);
        return this.mapLoanToLoanDto(this.save(loan));
    }

    @Override
    public LoanDto mapLoanToLoanDto(Loan loan) {
        return loanMapper.mapLoanToLoanDto(loan);
    }

    private Loan mapLoanDtoToLoan(LoanDto loanDto) {
        return loanMapper.mapLoanDtoToLoan(loanDto);
    }

    private List<Loan> mapLoanDtosToLoans(List<LoanDto> loanDtos) {

        List<Loan> loans = new ArrayList<>();
        loanDtos.forEach(loanDto -> loans.add(mapLoanDtoToLoan(loanDto)));

        return loans;
    }

    private List<LoanDto> mapLoansToLoanDtos(List<Loan> loans) {

        List<LoanDto> loanDtos = new ArrayList<>();
        loans.forEach(loan -> loanDtos.add(mapLoanToLoanDto(loan)));

        return loanDtos;
    }
}
