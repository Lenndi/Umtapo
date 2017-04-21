package org.lenndi.umtapo.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lenndi.umtapo.UmtapoApplication;
import org.lenndi.umtapo.dao.BorrowerDao;
import org.lenndi.umtapo.dto.BorrowerDto;
import org.lenndi.umtapo.dto.SimpleBorrowerDto;
import org.lenndi.umtapo.dto.SimpleLibraryDto;
import org.lenndi.umtapo.dto.SubscriptionDto;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.service.specific.BorrowerService;
import org.lenndi.umtapo.service.specific.LibraryService;
import org.lenndi.umtapo.service.specific.SubscriptionService;
import org.lenndi.umtapo.solr.SolrTestConfig;
import org.lenndi.umtapo.solr.repository.SolrBorrowerRepository;
import org.lenndi.umtapo.solr.repository.SolrRecordRepository;
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
        this.libraryService.save(this.utilCreator.createLibrary(1, false));
        SimpleLibraryDto library = this.utilCreator.createSimpleLibraryDto(1, false);

        BorrowerDto borrowerDto = this.utilCreator.createBorrowerDto(1, "Michel Test", "michel@test.com");
        BorrowerDto borrowerDto1 = this.utilCreator.createBorrowerDto(2, "Francis test", "francis@test.com");
        this.borrowerService.saveDto(borrowerDto);
        this.borrowerService.saveDto(borrowerDto1);

        SimpleBorrowerDto simpleBorrowerDto = this.utilCreator.createSimpleBorrowerDto(1, "Michel Test", "michel@test.com");
        SimpleBorrowerDto simpleBorrowerDto1 = this.utilCreator.createSimpleBorrowerDto(2, "Francis test", "francis@test.com");

        SubscriptionDto subscription = this.utilCreator.createSubscriptionDto(1, simpleBorrowerDto, library);
        SubscriptionDto subscription1 = this.utilCreator.createSubscriptionDto(2, simpleBorrowerDto1, library);
        this.subscriptionService.saveDto(subscription);
        this.subscriptionService.saveDto(subscription1);

        Pageable page = new PageRequest(0, 10);
        Page<BorrowerDto> borrowerDtoPage = this.borrowerService.findAllBorrowerDtoWithFilters("", "", "Rennes", "", null, null, page);
        Assert.assertEquals(2, borrowerDtoPage.getTotalElements());

        borrowerDtoPage = this.borrowerService.findAllBorrowerDtoWithFilters("", "francis", "Rennes", "", null, null, page);
        Assert.assertEquals(1, borrowerDtoPage.getTotalElements());
    }
}
