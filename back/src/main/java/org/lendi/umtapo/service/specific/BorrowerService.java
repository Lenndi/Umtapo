package org.lendi.umtapo.service.specific;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
     * @param nameOrEmail the contains
     * @param pageable    the pageable
     * @return the page
     */
    Page<BorrowerDto> findAllBorrowerDtoByNameOrEmail(String nameOrEmail, Pageable pageable);

    /**
     * Find all borrower dto with filters page.
     *
     * @param name                 the name
     * @param email                the email
     * @param city                 the city
     * @param id                   the id
     * @param fromSubscriptionEnd the start subscription end
     * @param toSubscriptionEnd   the end subscription end
     * @param page                 the page
     * @return the page
     */
    Page<BorrowerDto> findAllBorrowerDtoWithFilters(
            String name,
            String email,
            String city,
            String id,
            String fromSubscriptionEnd,
            String toSubscriptionEnd,
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
     * @throws IOException        the io exception
     * @throws JsonPatchException the json patch exception
     */
    BorrowerDto patchBorrower(JsonNode jsonNodeBorrower, Borrower borrower) throws IOException,
            JsonPatchException;

    }
