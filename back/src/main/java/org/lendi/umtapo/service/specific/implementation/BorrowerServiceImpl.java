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
 * Borrower service implementation.
 * <p>
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
    @Override
    public Borrower save(Borrower borrower) {
        return this.borrowerDao.save(borrower);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BorrowerDto save(BorrowerDto borrowerDto) {
        Borrower borrower = this.borrowerMapper.mapBorrowerDtoToBorrower(borrowerDto);
        borrower = this.borrowerDao.save(borrower);
        return this.borrowerMapper.mapBorrowerToBorrowerDto(borrower);
    }

    /**
     * {@inheritDoc}
     */
    public BorrowerDto find(Integer id, boolean isDto) {
        Borrower borrower = this.borrowerDao.findOne(id);
        return borrowerMapper.mapBorrowerToBorrowerDto(borrower);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Borrower find(Integer id) {
        return this.borrowerDao.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BorrowerDto> findAll(boolean isDto) {
        List<Borrower> borrowers = this.borrowerDao.findAll();
        return this.mapBorrowersToBorrowerDtos(borrowers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Borrower> findAll() {
        List<Borrower> borrowers = this.borrowerDao.findAll();
        return borrowers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean exists(Borrower borrower) {
        return this.borrowerDao.exists(borrower.getId());

    }

    /**
     * {@inheritDoc}
     */
    public Boolean exists(BorrowerDto borrowerDto) {
        return this.borrowerDao.exists(borrowerDto.getId());
    }

    /**
     * {@inheritDoc}
     */
    private Borrower mapBorrowerDtoToBorrower(BorrowerDto borrowerDto) {
        return borrowerMapper.mapBorrowerDtoToBorrower(borrowerDto);
    }

    /**
     * {@inheritDoc}
     */
    private BorrowerDto mapBorrowerToBorrowerDto(Borrower borrower) {
        return borrowerMapper.mapBorrowerToBorrowerDto(borrower);
    }

    /**
     * {@inheritDoc}
     */
    private List<Borrower> mapBorrowerDtosToBorrowers(List<BorrowerDto> borrowerDtos) {
        List<Borrower> borrowers = new ArrayList<>();

        borrowerDtos.forEach(borrowerDto -> borrowers.add(mapBorrowerDtoToBorrower(borrowerDto)));
        return borrowers;
    }

    /**
     * {@inheritDoc}
     */
    private List<BorrowerDto> mapBorrowersToBorrowerDtos(List<Borrower> borrowers) {
        List<BorrowerDto> borrowerDtos = new ArrayList<>();

        borrowers.forEach(borrower -> borrowerDtos.add(mapBorrowerToBorrowerDto(borrower)));
        return borrowerDtos;
    }
}
