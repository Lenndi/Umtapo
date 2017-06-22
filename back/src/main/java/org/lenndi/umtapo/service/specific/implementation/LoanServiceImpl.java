package org.lenndi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import org.lenndi.umtapo.dao.LoanDao;
import org.lenndi.umtapo.dto.LoanDto;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.entity.Library;
import org.lenndi.umtapo.entity.Loan;
import org.lenndi.umtapo.exception.CreateLoanException;
import org.lenndi.umtapo.exception.NotLoannableException;
import org.lenndi.umtapo.mapper.LoanMapper;
import org.lenndi.umtapo.service.generic.AbstractGenericService;
import org.lenndi.umtapo.service.specific.BorrowerService;
import org.lenndi.umtapo.service.specific.ItemService;
import org.lenndi.umtapo.service.specific.LibraryService;
import org.lenndi.umtapo.service.specific.LoanService;
import org.lenndi.umtapo.solr.document.BorrowerDocument;
import org.lenndi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Date;
import java.time.ZonedDateTime;
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
    private final LibraryService libraryService;
    private final BorrowerService borrowerService;

    /**
     * Instantiates a new Loan service.
     *
     * @param loanMapper          the loan mapper
     * @param loanDao             the loan dao
     * @param solrBorrowerService BorrowerDocument repository
     * @param itemService         the item service
     * @param libraryService      the library service
     * @param borrowerService     the borrower service
     */
    @Autowired
    public LoanServiceImpl(
            LoanMapper loanMapper,
            LoanDao loanDao,
            SolrBorrowerService solrBorrowerService,
            ItemService itemService,
            LibraryService libraryService,
            BorrowerService borrowerService) {
        Assert.notNull(loanMapper);
        Assert.notNull(loanDao);
        Assert.notNull(solrBorrowerService);
        Assert.notNull(itemService);
        Assert.notNull(borrowerService);
        Assert.notNull(libraryService);

        this.borrowerService = borrowerService;
        this.loanDao = loanDao;
        this.loanMapper = loanMapper;
        this.solrBorrowerService = solrBorrowerService;
        this.itemService = itemService;
        this.libraryService = libraryService;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanDto saveDto(LoanDto loanDto) {

        return this.loanMapper.mapLoanToLoanDto(this.saveLoanDtoToLoan(loanDto));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Loan saveLoanDtoToLoan(LoanDto loanDto) {
        Loan loan = this.loanMapper.mapLoanDtoToLoan(loanDto);

        return this.saveLoan(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Loan saveLoan(Loan loan) {

        Loan loanResult = this.save(loan);

        Integer borrowerId = loan.getBorrower().getId();
        BorrowerDocument borrowerDocument = this.solrBorrowerService.findById(borrowerId.toString());
        borrowerDocument.setNbLoans(this.findAllDtoByBorrowerIdAndNotReturned(borrowerId).size());

        if (borrowerDocument.getNbLoans() > borrowerDocument.getQuota()) {
            borrowerDocument.setTooMuchLoans(true);
        } else {
            borrowerDocument.setTooMuchLoans(false);
        }

        Loan olderLoanToReturn = this.loanDao.findFirstByBorrowerIdAndReturnedFalseOrderByEndAsc(borrowerId);
        borrowerDocument.setOlderReturn(Date.from(olderLoanToReturn.getEnd().toInstant()));
        this.solrBorrowerService.saveToIndex(borrowerDocument);

        return loanResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanDto saveLoanToLoanDto(Loan loan) {
        loan = this.save(loan);

        return this.loanMapper.mapLoanToLoanDto(loan);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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
        return loanDao.setEndById(loanDto.getEnd(), loanDto.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Loan createLoan(Item item, Integer borrowerId) throws CreateLoanException, NotLoannableException {

        Loan loan = new Loan();

        if (item != null && !item.getBorrowed() && item.getLoanable()) {

            item.setBorrowed(true);
            item = this.itemService.save(item);
            Borrower borrower = this.borrowerService.findOne(borrowerId);

            loan.setReturned(false);
            loan.setStart(ZonedDateTime.now());
            Library library = this.libraryService.findOne(item.getLibrary().getId());
            ZonedDateTime end = ZonedDateTime.now();
            end = end.plusDays(library.getBorrowDuration());
            loan.setEnd(end);
            loan.setBorrower(borrower);
            loan.setItem(item);
            loan = this.saveLoan(loan);

        } else if (item != null && !item.getLoanable()) {
            throw new NotLoannableException("This item it's not loannable : item = " + item);
        } else {
            throw new CreateLoanException("Create Loan Exception : item = " + item);
        }
        return this.saveLoan(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Loan backLoan(Item item) {

        Loan loan = new Loan();
        if (item != null && item.getBorrowed()) {
            loan = item.getLoans().get(item.getLoans().size() - 1);
            loan.setReturned(true);
            item.setBorrowed(false);
            this.itemService.save(item);
            this.save(loan);
        }
        return loan;
    }


    /**
     * {@inheritDoc}
     *
     * @return the loan
     */
    public Loan backLoan() {
        return null;
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
