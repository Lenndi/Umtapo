package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.service.generic.GenericService;
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
     * Save one borrower.
     * @param borrowerDto
     * @return
     */
    public BorrowerDto save(BorrowerDto borrowerDto);

    /**
     * Find one borrower.
     *
     * @param id
     * @return
     */
    public BorrowerDto find(Integer id);

    /**
     * Find all borrower.
     */
    public List<BorrowerDto> findAlls();
}
