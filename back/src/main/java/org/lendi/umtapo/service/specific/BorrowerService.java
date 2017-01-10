package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Borrower service.
 * <p>
 * Created by axel on 05/12/16.
 */
@Service
public interface BorrowerService extends GenericService<Borrower, Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    Borrower save(Borrower entity);

    /**
     * Save Borrower based on DTO.
     *
     * @param entity the entity
     * @return borrower dto
     */
    BorrowerDto saveDto(BorrowerDto entity);

    /**
     * {@inheritDoc}
     */
    @Override
    Borrower findOne(Integer integer);

    /**
     * Return a Borrower DTO by id.
     *
     * @param integer the integer
     * @return borrower dto
     */
    BorrowerDto findOneDto(Integer integer);


    /**
     * {@inheritDoc}
     */
    @Override
    List<Borrower> findAll();

    /**
     * Find all pageable page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<Borrower> findAllPageable(Pageable pageable);

    /**
     * Return a page of Borrower.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<BorrowerDto> findAllPageableDto(Pageable pageable);

    /**
     * Retur all Borrower DTO.
     *
     * @return list list
     */
    List<BorrowerDto> findAllDto();
}
