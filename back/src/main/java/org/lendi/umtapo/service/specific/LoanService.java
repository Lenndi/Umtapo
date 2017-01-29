package org.lendi.umtapo.service.specific;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import org.lendi.umtapo.dto.LoanDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.entity.Loan;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by axel on 22/01/17.
 */
@Service
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
    List<LoanDto> findAllDtoByBorrowerIdAndReturned(Integer id);

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
     * @throws IOException        the io exception
     * @throws JsonPatchException the json patch exception
     */
    LoanDto patchLoan(JsonNode jsonNodeBorrower, Loan loan) throws IOException, JsonPatchException;
}

