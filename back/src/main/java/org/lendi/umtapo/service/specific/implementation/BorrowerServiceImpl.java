package org.lendi.umtapo.service.specific.implementation;

import org.lendi.umtapo.dao.BorrowerDao;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.mapper.BorrowerMapper;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Borrower service implementation.
 * <p>
 * Created by axel on 29/11/16.
 */
@Service
public class BorrowerServiceImpl extends AbstractGenericService<Borrower, Integer> implements BorrowerService {

    private final BorrowerDao borrowerDao;
    private final BorrowerMapper borrowerMapper;

    @Autowired
    public BorrowerServiceImpl(BorrowerDao borrowerDao, BorrowerMapper borrowerMapper) {
        Assert.notNull(borrowerDao);
        Assert.notNull(borrowerMapper);
        this.borrowerDao = borrowerDao;
        this.borrowerMapper = borrowerMapper;
    }

    @Override
    public BorrowerDto saveDto(BorrowerDto borrowerDto) {
        Borrower borrower = this.borrowerMapper.mapBorrowerDtoToBorrower(borrowerDto);
        borrower = this.borrowerDao.save(borrower);

        return this.borrowerMapper.mapBorrowerToBorrowerDto(borrower);
    }

    @Override
    public BorrowerDto findOneDto(Integer id) {
        Borrower borrower = this.borrowerDao.findOne(id);

        return borrowerMapper.mapBorrowerToBorrowerDto(borrower);
    }

    @Override
    public List<BorrowerDto> findAllDto() {
        List<Borrower> borrowers = this.borrowerDao.findAll();

        return this.mapBorrowersToBorrowerDtos(borrowers);
    }

    private Borrower mapBorrowerDtoToBorrower(BorrowerDto borrowerDto) {
        return borrowerMapper.mapBorrowerDtoToBorrower(borrowerDto);
    }

    private BorrowerDto mapBorrowerToBorrowerDto(Borrower borrower) {
        return borrowerMapper.mapBorrowerToBorrowerDto(borrower);
    }

    private List<Borrower> mapBorrowerDtosToBorrowers(List<BorrowerDto> borrowerDtos) {
        List<Borrower> borrowers = new ArrayList<>();

        borrowerDtos.forEach(borrowerDto -> borrowers.add(mapBorrowerDtoToBorrower(borrowerDto)));

        return borrowers;
    }

    private List<BorrowerDto> mapBorrowersToBorrowerDtos(List<Borrower> borrowers) {
        List<BorrowerDto> borrowerDtos = new ArrayList<>();

        borrowers.forEach(borrower -> borrowerDtos.add(mapBorrowerToBorrowerDto(borrower)));

        return borrowerDtos;
    }
}
