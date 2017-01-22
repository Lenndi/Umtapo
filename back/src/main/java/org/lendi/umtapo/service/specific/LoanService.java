package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.dto.LoanDto;
import org.lendi.umtapo.entity.Loan;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.stereotype.Service;

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
}
