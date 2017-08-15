package org.lenndi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import org.lenndi.umtapo.dao.LoanDao;
import org.lenndi.umtapo.dto.LoanDto;
import org.lenndi.umtapo.dto.SimpleLoanDto;
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

        this.borrowerService = borrowerService;
        this.loanDao = loanDao;
        this.loanMapper = loanMapper;
        this.solrBorrowerService = solrBorrowerService;
        this.itemService = itemService;
        this.libraryService = libraryService;

    }

    @Override
    public LoanDto saveDto(LoanDto loanDto) {

        return this.loanMapper.mapLoanToLoanDto(this.saveLoanDtoToLoan(loanDto));
    }

    @Override
    @Transactional
    public Loan saveLoanDtoToLoan(LoanDto loanDto) {
        Loan loan = this.loanMapper.mapLoanDtoToLoan(loanDto);

        return this.saveLoan(loan);
    }

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

    @Override
    public List<LoanDto> findAllDto() {
        List<Loan> loans = this.findAll();

        return this.mapLoansToLoanDtos(loans);
    }

    @Override
    public List<SimpleLoanDto> findAllDtoByBorrowerIdAndNotReturned(Integer id) {
        List<Loan> loans = loanDao.findByBorrowerIdAndReturnedFalse(id);
        loans.forEach(loan -> loan.setItem(this.itemService.linkRecord(loan.getItem())));

        return this.mapLoansToSimpleLoanDtos(loans);
    }

    @Override
    public List<SimpleLoanDto> findAllDtoByBorrowerTagIdAndNotReturned(String tagId) {
        List<Loan> loans = loanDao.findByBorrowerTagIdAndNotReturned(tagId);
        loans.forEach(loan -> loan.setItem(this.itemService.linkRecord(loan.getItem())));

        return this.mapLoansToSimpleLoanDtos(loans);
    }

    /**
     * Save end date
     * @param loanDto the loan dto
     * @return int
     */
    public Integer saveEnd(LoanDto loanDto) {
        return loanDao.setEndById(loanDto.getEnd(), loanDto.getId());
    }

    /**
     * Create loan
     *
     * @param item       the item
     * @param borrowerId the borrower id
     * @return loan
     * @throws CreateLoanException exception
     * @throws NotLoannableException exception
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
     * Loan return.
     *
     * @param item the item
     * @return loan
     */
    @Transactional
    public Loan loanReturn(Item item) {

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
     * Patch a loan.
     * @param jsonNodeLoan json node loan
     * @param loan         the loan
     * @return loan dto
     * @throws IllegalAccessException exception
     */
    public LoanDto patchLoan(JsonNode jsonNodeLoan, Loan loan) throws IllegalAccessException {

        loanMapper.mergeLoanAndJsonNode(loan, jsonNodeLoan);
        return loanMapper.mapLoanToLoanDto(loan);
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
