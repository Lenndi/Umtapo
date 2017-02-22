package org.lendi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import org.lendi.umtapo.dao.LoanDao;
import org.lendi.umtapo.dto.LoanDto;
import org.lendi.umtapo.entity.Loan;
import org.lendi.umtapo.mapper.LoanMapper;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.LoanService;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
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

    /**
     * Instantiates a new Loan service.
     *
     * @param loanMapper the loan mapper
     * @param loanDao    the loan dao
     * @param solrBorrowerService BorrowerDocument repository
     */
    @Autowired
    public LoanServiceImpl(LoanMapper loanMapper, LoanDao loanDao, SolrBorrowerService solrBorrowerService) {
        Assert.notNull(loanMapper);
        Assert.notNull(loanDao);
        Assert.notNull(solrBorrowerService);

        this.loanDao = loanDao;
        this.loanMapper = loanMapper;
        this.solrBorrowerService = solrBorrowerService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanDto saveDto(LoanDto loanDto) {
        Loan loan = this.loanMapper.mapLoanDtoToLoan(loanDto);
        loan = this.save(loan);

        Integer borrowerId = loanDto.getBorrower().getId();
        BorrowerDocument borrowerDocument = this.solrBorrowerService.findById(borrowerId.toString());
        borrowerDocument.setNbLoans(this.findAllDtoByBorrowerIdAndReturned(borrowerId).size());

        if (borrowerDocument.getNbLoans() > borrowerDocument.getQuota()) {
            borrowerDocument.setTooMuchLoans(true);
        } else {
            borrowerDocument.setTooMuchLoans(false);
        }

        Loan olderLoanToReturn = this.loanDao.findFirstByBorrowerIdAndReturnedFalseOrderByEndAsc(borrowerId);
        borrowerDocument.setOlderReturn(olderLoanToReturn.getEnd());
        this.solrBorrowerService.saveToIndex(borrowerDocument);

        return this.loanMapper.mapLoanToLoanDto(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanDto findOneDto(Integer id) {
        Loan loan = this.findOne(id);

        return loanMapper.mapLoanToLoanDto(loan);
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
    public List<LoanDto> findAllDtoByBorrowerIdAndReturned(Integer id) {
        List<Loan> loans = loanDao.findByBorrowerIdAndReturnedFalse(id);

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
    public LoanDto patchLoan(JsonNode jsonNodeLoan, Loan loan) throws IOException, JsonPatchException {

        loanMapper.mergeLoanAndJsonNode(loan, jsonNodeLoan);
        return this.mapLoanToLoanDto(this.save(loan));
    }

    private Loan mapLoanDtoToLoan(LoanDto loanDto) {
        return loanMapper.mapLoanDtoToLoan(loanDto);
    }

    private LoanDto mapLoanToLoanDto(Loan loan) {
        return loanMapper.mapLoanToLoanDto(loan);
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
