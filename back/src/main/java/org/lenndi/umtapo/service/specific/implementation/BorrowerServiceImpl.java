package org.lenndi.umtapo.service.specific.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import org.lenndi.umtapo.dao.BorrowerDao;
import org.lenndi.umtapo.dto.BorrowerDto;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.mapper.BorrowerMapper;
import org.lenndi.umtapo.service.generic.AbstractGenericService;
import org.lenndi.umtapo.service.specific.BorrowerService;
import org.lenndi.umtapo.solr.document.BorrowerDocument;
import org.lenndi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    @Transactional
    public Borrower save(Borrower entity) {
        Borrower borrower = super.save(entity);
        this.solrBorrowerService.saveToIndex(borrower);

        return borrower;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BorrowerDto saveDto(BorrowerDto borrowerDto) {
        Borrower borrower = this.borrowerMapper.mapBorrowerDtoToBorrower(borrowerDto);
        borrower = this.save(borrower);

        return this.borrowerMapper.mapBorrowerToBorrowerDto(borrower);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateDto(BorrowerDto borrowerDto) {
        Borrower borrower = this.borrowerMapper.mapBorrowerDtoToBorrower(borrowerDto);
        borrower = this.save(borrower);
        this.solrBorrowerService.updateToIndex(borrower);
    }

    @Override
    @Transactional
    public void delete(Integer borrowerId) throws NoSuchElementException {

        Borrower borrower = this.borrowerDao.findOne(borrowerId);

        if (borrower != null) {
            borrower.setActive(false);
            borrower.setName("Anonymous");
            borrower.getAddress().setEmail("ano@ny.mous");
            borrower.getAddress().setAddress1("anonymous");
            borrower.getAddress().setAddress2("");
            borrower.getAddress().setPhone("0000000000");
            this.borrowerDao.save(borrower);
            this.solrBorrowerService.deleteFromIndex(borrowerId);
        } else {
            throw new NoSuchElementException("Borrower does not exist");
        }
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

    @Override
    public Page<BorrowerDto> findAllDto(Pageable page) {
        Page<BorrowerDocument> borrowerDocuments = this.solrBorrowerService.searchAll(page);

        return this.borrowerDocumentPageToDtoPage(borrowerDocuments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BorrowerDto> findAllBorrowerDtoByNameOrEmail(String name, Pageable pageable) {
        Page<BorrowerDocument> borrowers = this.solrBorrowerService.searchByNameOrEmail(name, pageable);

        return this.borrowerDocumentPageToDtoPage(borrowers);
    }

    @Override
    public Page<BorrowerDto> findAllBorrowerDtoWithFilters(
            String name,
            String email,
            String city,
            String id,
            Boolean tooMuchLoans,
            Boolean lateness,
            Pageable page
    ) {
        Page<BorrowerDocument> borrowers = this.solrBorrowerService.fullSearch(
                name, email, city, id, tooMuchLoans, lateness, page);

        return this.borrowerDocumentPageToDtoPage(borrowers);
    }

    /**
     * {@inheritDoc}
     */
    public Page<BorrowerDto> findAllPageableDtoByName(Pageable pageable, String contains) {

        Page<Borrower> borrowerDtos = borrowerDao.findByNameContainingIgnoreCase(contains, pageable);
        return this.mapBorrowersToBorrowerDtosPage(borrowerDtos);
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
    public BorrowerDto patchBorrower(JsonNode jsonNodeBorrower, Borrower borrower) throws IllegalAccessException {

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

    private Page<BorrowerDto> borrowerDocumentPageToDtoPage(Page<BorrowerDocument> borrowersPage) {
        Pageable page = new PageRequest(borrowersPage.getNumber(), borrowersPage.getSize());
        List<BorrowerDto> borrowerDtos = new ArrayList<>();
        List<BorrowerDocument> borrowers = borrowersPage.getContent();

        borrowers.forEach(borrowerDocument ->
          borrowerDtos.add(this.borrowerMapper.mapBorrowerDocumentToBorrowerDto(borrowerDocument))
        );

        return new PageImpl<>(borrowerDtos, page, borrowersPage.getTotalElements());
    }

    private Page<BorrowerDto> mapBorrowersToBorrowerDtosPage(Page<Borrower> borrowers) {

        List<BorrowerDto> borrowerDtos = new ArrayList<>();
        borrowers.forEach(borrower -> borrowerDtos.add(mapBorrowerToBorrowerDto(borrower)));

        return  new PageImpl<>(borrowerDtos);
    }
}
