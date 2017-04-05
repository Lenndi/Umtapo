package org.lenndi.umtapo.service.specific;

import com.fasterxml.jackson.databind.JsonNode;
import org.lenndi.umtapo.dto.BorrowerDto;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.service.generic.GenericService;
import org.lenndi.umtapo.solr.exception.InvalidRecordException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
     * @throws InvalidRecordException the invalid record exception
     */
    BorrowerDto saveDto(BorrowerDto entity) throws InvalidRecordException;

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
     * Return a page of Borrower.
     *
     * @param nameOrEmail the contains
     * @param pageable    the pageable
     * @return the page
     */
    Page<BorrowerDto> findAllBorrowerDtoByNameOrEmail(String nameOrEmail, Pageable pageable);

    /**
     * Find all borrower dto with filters page.
     *
     * @param name         the name
     * @param email        the email
     * @param city         the city
     * @param id           the id
     * @param tooMuchLoans the too much loans
     * @param lateness     the lateness
     * @param page         the page
     * @return the page
     */
    Page<BorrowerDto> findAllBorrowerDtoWithFilters(
            String name,
            String email,
            String city,
            String id,
            Boolean tooMuchLoans,
            Boolean lateness,
            Pageable page);

    /**
     * Retur all Borrower DTO.
     *
     * @return list list
     */
    List<BorrowerDto> findAllDto();

    /**
     * Find all dto page.
     *
     * @param page the page
     * @return the page
     */
    Page<BorrowerDto> findAllDto(Pageable page);

    /**
     * Dynamic patch borrower.
     *
     * @param jsonNodeBorrower the json node borrower
     * @param borrower         the borrower
     * @return the borrower
     * @throws IllegalAccessException the illegal access exception
     */
    BorrowerDto patchBorrower(JsonNode jsonNodeBorrower, Borrower borrower) throws IllegalAccessException;

    @Override
    void delete(Integer integer) throws NoSuchElementException;

    /**
     * Find all pageable dto by name page.
     *
     * @param pageable the pageable
     * @param contains the contains
     * @return the page
     */
    Page<BorrowerDto> findAllPageableDtoByName(Pageable pageable, String contains);
}
