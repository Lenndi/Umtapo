package org.lendi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import org.lendi.umtapo.dao.BorrowerDao;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.mapper.BorrowerMapper;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.exception.InvalidRecordException;
import org.lendi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Borrower service implementation.
 * <p>
 * Created by axel on 29/11/16.
 */
@Service
public class BorrowerServiceImpl extends AbstractGenericService<Borrower, Integer> implements BorrowerService {

    private final BorrowerMapper borrowerMapper;
    private final BorrowerDao borrowerDao;
    private final SolrBorrowerService solrBorrowerService;

    /**
     * Instantiates a new Borrower service.
     *
     * @param borrowerMapper the borrower mapper
     * @param borrowerDao    the borrower dao
     * @param indexService   the index service
     */
    @Autowired
    public BorrowerServiceImpl(
            BorrowerMapper borrowerMapper,
            BorrowerDao borrowerDao,
            SolrBorrowerService indexService
    ) {
        Assert.notNull(borrowerMapper);
        Assert.notNull(indexService);
        Assert.notNull(borrowerDao);

        this.borrowerDao = borrowerDao;
        this.borrowerMapper = borrowerMapper;
        this.solrBorrowerService = indexService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BorrowerDto saveDto(BorrowerDto borrowerDto) throws InvalidRecordException {
        Borrower borrower = this.borrowerMapper.mapBorrowerDtoToBorrower(borrowerDto);
        borrower = this.save(borrower);
        this.solrBorrowerService.addToIndex(borrower);

        return this.borrowerMapper.mapBorrowerToBorrowerDto(borrower);
    }

    @Override
    public void delete(Integer borrowerId) {
        this.solrBorrowerService.deleteFromIndex(borrowerId);
        super.delete(borrowerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BorrowerDto findOneDto(Integer id) {
        Borrower borrower = this.findOne(id);

        return borrowerMapper.mapBorrowerToBorrowerDto(borrower);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BorrowerDto> findAllDto() {
        List<Borrower> borrowers = findAll();
        return this.mapBorrowersToBorrowerDtos(borrowers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BorrowerDto> findAllPageableDto(Pageable pageable, String contains) {
        Page<BorrowerDocument> borrowers;

        if (Objects.equals(contains, "")) {
            borrowers = this.solrBorrowerService.searchAll(pageable);
        } else {
            borrowers = this.solrBorrowerService.searchByName(contains, pageable);
        }

        return this.borrowerDocumentPageToDtoPage(borrowers, pageable);
    }

    /**
     * {@inheritDoc}
     */
    public Page<Borrower> findAllPageable(Pageable pageable) {
        return this.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    public BorrowerDto patchBorrower(JsonNode jsonNodeBorrower, Borrower borrower){

        borrowerMapper.mergeItemAndJsonNode(borrower, jsonNodeBorrower);
        return this.mapBorrowerToBorrowerDto(this.save(borrower));
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

    private Page<BorrowerDto> borrowerDocumentPageToDtoPage(Page<BorrowerDocument> borrowersPage, Pageable pageable) {
        List<BorrowerDto> borrowerDtos = new ArrayList<>();
        List<BorrowerDocument> borrowers = borrowersPage.getContent();

        borrowers.forEach(borrowerDocument ->
          borrowerDtos.add(this.borrowerMapper.mapBorrowerDocumenttoBorrowerDto(borrowerDocument))
        );

        return new PageImpl<>(borrowerDtos, pageable, borrowersPage.getTotalElements());
    }
}
