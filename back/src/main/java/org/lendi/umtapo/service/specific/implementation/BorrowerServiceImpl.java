package org.lendi.umtapo.service.specific.implementation;


import org.lendi.umtapo.dao.BorrowerDao;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.mapper.BorrowerMapper;
import org.lendi.umtapo.service.generic.implementation.GenericServiceImpl;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by axel on 29/11/16.
 */
@Service
public class BorrowerServiceImpl extends GenericServiceImpl<Borrower, Integer> implements BorrowerService {

    @Autowired
    private BorrowerDao borrowerDao;
    @Autowired
    private BorrowerMapper borrowerMapper;

    /**
     * {@inheritDoc}
     */
    public BorrowerDto save(BorrowerDto borrowerDto) {
        Borrower borrower = borrowerMapper.mapBorrowerDtoToBorrower(borrowerDto);
        return borrowerDto;
    }

    /**
     * {@inheritDoc}
     */
    public BorrowerDto find(Integer id) {
        Borrower borrower = borrowerDao.findOne(id);
        return borrowerMapper.mapBorrowerToBorrowerDto(borrower);

    }

    /**
     * {@inheritDoc}
     */
    public List<BorrowerDto> findAlls() {
    }

    /**
     * {@inheritDoc}
     */
    public Borrower mapBorrowerDtoToBorrower(BorrowerDto borrowerDto){
        return borrowerMapper.mapBorrowerDtoToBorrower(borrowerDto);
    }

    /**
     * {@inheritDoc}
     */
    public BorrowerDto mapBorrowerToBorrowerDto(Borrower borrower){
        return borrowerMapper.mapBorrowerToBorrowerDto(borrower);
    }

    /**
     * {@inheritDoc}
     */
    public Borrower mapBorrowerDtosToBorrowers(List<BorrowerDto> borrowerDtos){

        List<Borrower> borrowers;

                borrowerDtos.stream().collect();


    }
}
