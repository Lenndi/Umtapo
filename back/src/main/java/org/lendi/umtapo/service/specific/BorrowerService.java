package org.lendi.umtapo.service.specific;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.service.generic.GenericService;
import org.lendi.umtapo.solr.repository.SolrRepositoryException;
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
     * @throws SolrRepositoryException the solr repository exception
     */
    BorrowerDto saveDto(BorrowerDto entity) throws SolrRepositoryException;

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
     * @throws SolrRepositoryException the solr repository exception
     */
    BorrowerDto findOneDto(Integer integer) throws SolrRepositoryException;


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
     * @param contains the contains
     * @return the page
     * @throws SolrRepositoryException the solr repository exception
     */
    Page<BorrowerDto> findAllPageableDto(Pageable pageable, String contains) throws SolrRepositoryException;

    /**
     * Retur all Borrower DTO.
     *
     * @return list list
     */
    List<BorrowerDto> findAllDto();

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
