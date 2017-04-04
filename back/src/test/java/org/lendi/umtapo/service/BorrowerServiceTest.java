package org.lendi.umtapo.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lendi.umtapo.UmtapoApplication;
import org.lendi.umtapo.dao.BorrowerDao;
import org.lendi.umtapo.dao.LibraryDao;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.dto.LibraryDto;
import org.lendi.umtapo.dto.SubscriptionDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.entity.Library;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.lendi.umtapo.service.specific.LibraryService;
import org.lendi.umtapo.service.specific.SubscriptionService;
import org.lendi.umtapo.solr.SolrTestConfig;
import org.lendi.umtapo.solr.repository.SolrBorrowerRepository;
import org.lendi.umtapo.solr.repository.SolrRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import util.UtilCreator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UmtapoApplication.class)
@ContextConfiguration(classes = SolrTestConfig.class)
@Sql(value = "classpath:test/truncate.sql")
public class BorrowerServiceTest {

    @Autowired
    private BorrowerDao borrowerDao;
    @Autowired
    private BorrowerService borrowerService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private LibraryService libraryService;
    private UtilCreator utilCreator;
    @Autowired
    SolrBorrowerRepository solrBorrowerRepository;
    @Autowired
    SolrRecordRepository solrRecordRepository;

    public BorrowerServiceTest() {
        this.utilCreator = new UtilCreator();
    }

    @Before
    public void setup() {
        solrBorrowerRepository.deleteAll();
        solrRecordRepository.deleteAll();
    }

    @After
    public void tearDown(){}

    @Test
    public void testSaveDto() throws Exception {
        BorrowerDto borrowerDto = this.utilCreator.createBorrowerDto(1);

        this.borrowerService.saveDto(borrowerDto);
        Borrower savedBorrower = this.borrowerDao.findOne(1);

        Assert.assertNotNull(savedBorrower);
        Assert.assertEquals(1, savedBorrower.getId().longValue());
    }

    @Test
    public void testFindAllDto() throws Exception {
        Borrower borrower = this.utilCreator.createBorrower(1);
        Borrower borrower1 = this.utilCreator.createBorrower(2);

        this.borrowerService.save(borrower);
        this.borrowerService.save(borrower1);

        Pageable page = new PageRequest(0, 10);
        Page<BorrowerDto> borrowerDtoPage = this.borrowerService.findAllDto(page);

        Assert.assertEquals(2, borrowerDtoPage.getTotalElements());
    }

    @Test
    public void testFindAllBorrowerDtoByNameOrEmail() throws Exception {
        BorrowerDto borrower = this.utilCreator.createBorrowerDto(1, "Michel Test", "michel@test.com");
        BorrowerDto borrower1 = this.utilCreator.createBorrowerDto(2, "Francis test", "francis@test.com");

        this.borrowerService.saveDto(borrower);
        this.borrowerService.saveDto(borrower1);

        Pageable page = new PageRequest(0, 10);
        Page<BorrowerDto> borrowerDtoPage = this.borrowerService.findAllBorrowerDtoByNameOrEmail("test", page);
        Assert.assertEquals(2, borrowerDtoPage.getTotalElements());

        borrowerDtoPage = this.borrowerService.findAllBorrowerDtoByNameOrEmail("francis", page);
        Assert.assertEquals(1, borrowerDtoPage.getTotalElements());

        borrowerDtoPage = this.borrowerService.findAllBorrowerDtoByNameOrEmail(".com", page);
        Assert.assertEquals(2, borrowerDtoPage.getTotalElements());
    }

    @Test
    public void testFindAllBorrowerDtoWithFilters() throws Exception {
        LibraryDto library = this.libraryService.saveDto(this.utilCreator.createLibraryDto(1, false));

        BorrowerDto borrower = this.utilCreator.createBorrowerDto(1, "Michel Test", "michel@test.com");
        BorrowerDto borrower1 = this.utilCreator.createBorrowerDto(2, "Francis test", "francis@test.com");
        this.borrowerService.saveDto(borrower);
        this.borrowerService.saveDto(borrower1);
        SubscriptionDto subscription = this.utilCreator.createSubscriptionDto(1, borrower, library);
        SubscriptionDto subscription1 = this.utilCreator.createSubscriptionDto(2, borrower1, library);
        this.subscriptionService.saveDto(subscription);
        this.subscriptionService.saveDto(subscription1);

        Pageable page = new PageRequest(0, 10);
        Page<BorrowerDto> borrowerDtoPage = this.borrowerService.findAllBorrowerDtoWithFilters("", "", "Rennes", "", null, null, page);
        Assert.assertEquals(2, borrowerDtoPage.getTotalElements());

        borrowerDtoPage = this.borrowerService.findAllBorrowerDtoWithFilters("", "francis", "Rennes", "", null, null, page);
        Assert.assertEquals(1, borrowerDtoPage.getTotalElements());
    }
}
