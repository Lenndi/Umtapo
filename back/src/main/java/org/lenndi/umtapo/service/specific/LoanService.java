package org.lenndi.umtapo.service.specific;

import com.fasterxml.jackson.databind.JsonNode;
import org.lenndi.umtapo.dto.LoanDto;
import org.lenndi.umtapo.dto.SimpleLoanDto;
import org.lenndi.umtapo.entity.Loan;
import org.lenndi.umtapo.service.generic.GenericService;

import java.util.List;

/**
 * Created by axel on 22/01/17.
 */
public interface LoanService extends GenericService<Loan, Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    Loan save(Loan entity);

    /**
     * Save Loan based on DTO.
     *
     * @param entity the entity
     * @return loan dto
     */
    LoanDto saveDto(LoanDto entity);

    /**
     * Save loan dto to loan loan.
     *
     * @param loanDto the loan dto
     * @return the loan
     */
    Loan saveLoanDtoToLoan(LoanDto loanDto);

    /**
     * Save loan to loan dto loan.
     *
     * @param loan the loan
     * @return the loan
     */
    LoanDto saveLoanToLoanDto(Loan loan);

    /**
     * {@inheritDoc}
     */
    @Override
    Loan findOne(Integer integer);

    /**
     * Return a Loan DTO by id.
     *
     * @param integer the integer
     * @return loan dto
     */
    LoanDto findOneDto(Integer integer);


    /**
     * {@inheritDoc}
     */
    @Override
    List<Loan> findAll();


    /**
     * Retur all Loan DTO.
     *
     * @return list list
     */
    List<LoanDto> findAllDto();

    /**
     * The Find all dto by borrower id and returned.
     *
     * @param id the id
     * @return the list
     */
    List<SimpleLoanDto> findAllDtoByBorrowerIdAndNotReturned(Integer id);

    /**
     * Item End Date change.
     *
     * @param loanDto the loan dto
     * @return the integer
     */
    Integer saveEnd(LoanDto loanDto);

    /**
     * Dynamic patch borrower.
     *
     * @param jsonNodeBorrower the json node borrower
     * @param loan             the loan
     * @return the borrower
     * @throws IllegalAccessException the illegal access exception
     */
    LoanDto patchLoan(JsonNode jsonNodeBorrower, Loan loan) throws IllegalAccessException;
}

