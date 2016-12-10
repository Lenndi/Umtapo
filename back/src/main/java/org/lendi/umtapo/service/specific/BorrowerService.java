package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Borrower service.
 *
 * Created by axel on 05/12/16.
 */
@Service
public interface BorrowerService extends GenericService<Borrower, Integer> {

    /**
     * Persist a Borrower.
     *
     * @param borrower
     * @return BorrowerDto
     */
    Borrower save(Borrower borrower);

    /**
     * Persist a Borrower from a BorrowerDto.
     *
     * @param borrowerDto
     * @return
     */
    BorrowerDto save(BorrowerDto borrowerDto);

    /**
     * Find a Borrower by id.
     *
     * @param id
     * @return BorrowerDto
     */
    Borrower find(Integer id);

    /**
     * Find a Borrower by id.
     *
     * @param id
     * @param isDto Return a BorrowerDto entity if true.
     * @return BorrowerDto
     */
    BorrowerDto find(Integer id, boolean isDto);

    /**
     * Find all Libraries.
     *
     * @return List<BorrowerDto>
     */
    List<Borrower> findAll();

    /**
     * Find all Libraries.
     *
     * @param isDto Return a BorrowerDto if true.
     * @return
     */
    List<BorrowerDto> findAll(boolean isDto);

    /**
     * Check if Borrower exist.
     *
     * @param borrower
     * @return true if exist.
     */
    Boolean exists(Borrower borrower);

    /**
     * Check if Borrower exist.
     *
     * @param borrowerDto
     * @return true if exist.
     */
    Boolean exists(BorrowerDto borrowerDto);
}
