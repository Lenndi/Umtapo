package org.lendi.umtapo.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lendi.umtapo.UmtapoApplication;
import org.lendi.umtapo.dao.BorrowerDao;
import org.lendi.umtapo.dao.LibraryDao;
import org.lendi.umtapo.dao.SubscriptionDao;
import org.lendi.umtapo.dto.SubscriptionDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.entity.Library;
import org.lendi.umtapo.entity.Subscription;
import org.lendi.umtapo.service.specific.SubscriptionService;
import org.lendi.umtapo.solr.SolrTestConfig;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.SolrBorrowerRepository;
import org.lendi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import util.UtilCreator;

import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UmtapoApplication.class)
@ContextConfiguration(classes = SolrTestConfig.class)
@Sql(value = "classpath:test/truncate.sql")
public class SubscriptionServiceTest {
    public static final String BAD_SUBSCRIPTION_DATE = "org.lendi.umtapo.exception.BadSubscriptionDateException";

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private BorrowerDao borrowerDao;
    @Autowired
    private LibraryDao libraryDao;
    @Autowired
    private SubscriptionDao subscriptionDao;
    @Autowired
    private SolrBorrowerService solrBorrowerService;
    @Autowired
    private SolrBorrowerRepository solrBorrowerRepository;
    private UtilCreator utilCreator;
    private Borrower borrower;
    private Library library;

    @Before
    public void setup() throws Exception {
        solrBorrowerRepository.deleteAll();
        this.utilCreator = new UtilCreator();
        BorrowerDocument borrowerDocument = new BorrowerDocument();
        borrowerDocument.setId("1");
        solrBorrowerService.saveToIndex(borrowerDocument);
        library = utilCreator.createLibrary(1, false);
        borrower = utilCreator.createBorrower(1);
        library = libraryDao.save(library);
        borrower = borrowerDao.save(borrower);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
        Subscription subscription = utilCreator.createSubscription(1, borrower, library);
        subscriptionService.save(subscription);

        BorrowerDocument borrowerDocument1 = solrBorrowerService.findById("1");
        Assert.assertEquals(Date.from(subscription.getEnd().toInstant()), borrowerDocument1.getSubscriptionEnd());
        Assert.assertEquals(Date.from(subscription.getStart().toInstant()), borrowerDocument1.getSubscriptionStart());
        Assert.assertEquals(borrowerDocument1.getLibraryId().longValue(), 1);
        Assert.assertEquals(borrowerDocument1.getLibraryName(), "Test Library");
    }

    @Test
    public void testSaveDto() throws Exception {
        SubscriptionDto subscriptionDto = subscriptionService.saveDto(utilCreator.createSubscriptionDto(1, utilCreator.createBorrowerDto(1), utilCreator.createLibraryDto(1)));

        Subscription subscription = subscriptionDao.findOne(subscriptionDto.getId());

        Assert.assertNotNull(subscription);
        Assert.assertEquals(subscription.getContribution().longValue(), 650);
        Assert.assertEquals(subscription.getStart(), utilCreator.getSubscriptionStart());
        Assert.assertEquals(subscription.getEnd(), utilCreator.getSubscriptionEnd());
        Assert.assertEquals(subscription.getLibrary().getId(), library.getId());
        Assert.assertEquals(subscription.getBorrower().getId(), borrower.getId());
    }

    @Test
    public void testCantSaveSubscriptionInAnExistingRange() throws Exception {
        boolean isExceptionThrown = false;
        String exceptionName = "";

        ZonedDateTime subscriptionStart1 = java.time.ZonedDateTime.of(2016, 10, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime subscriptionEnd1 = java.time.ZonedDateTime.of(2017, 10, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime subscriptionStart2 = java.time.ZonedDateTime.of(2016, 12, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime subscriptionEnd2 = java.time.ZonedDateTime.of(2017, 01, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime subscriptionStart3 = java.time.ZonedDateTime.of(2018, 10, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime subscriptionEnd3 = java.time.ZonedDateTime.of(2019, 10, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime subscriptionStart4 = java.time.ZonedDateTime.of(2015, 10, 1, 0, 0, 0, 0, ZoneId.systemDefault());


        Subscription subscription = new Subscription();
        subscription.setLibrary(library);
        subscription.setBorrower(borrower);
        subscription.setContribution(10);
        subscription.setStart(subscriptionStart1);
        subscription.setEnd(subscriptionEnd1);
        subscriptionDao.save(subscription);

        Subscription subscription1 = new Subscription();
        subscription1.setLibrary(library);
        subscription1.setBorrower(borrower);
        subscription1.setContribution(10);
        subscription1.setStart(subscriptionStart2);
        subscription1.setEnd(subscriptionEnd3);
        try {
            subscriptionService.save(subscription1);
        } catch (Exception e) {
            isExceptionThrown = true;
            exceptionName = e.getClass().getName();
        }
        Assert.assertTrue(isExceptionThrown);
        Assert.assertEquals(exceptionName, BAD_SUBSCRIPTION_DATE);

        isExceptionThrown = false;
        exceptionName = "";
        Subscription subscription2 = new Subscription();
        subscription2.setLibrary(library);
        subscription2.setBorrower(borrower);
        subscription2.setContribution(10);
        subscription2.setStart(subscriptionStart4);
        subscription2.setEnd(subscriptionEnd2);
        try {
            subscriptionService.save(subscription2);
        } catch (Exception e) {
            isExceptionThrown = true;
            exceptionName = e.getClass().getName();
        }
        Assert.assertTrue(isExceptionThrown);
        Assert.assertEquals(exceptionName, BAD_SUBSCRIPTION_DATE);

        isExceptionThrown = false;
        exceptionName = "";
        Subscription subscription3 = new Subscription();
        subscription3.setLibrary(library);
        subscription3.setBorrower(borrower);
        subscription3.setContribution(10);
        subscription3.setStart(subscriptionStart3);
        subscription3.setEnd(subscriptionEnd1);
        try {
            subscriptionService.save(subscription3);
        } catch (Exception e) {
            isExceptionThrown = true;
            exceptionName = e.getClass().getName();
        }
        Assert.assertTrue(isExceptionThrown);
        Assert.assertEquals(exceptionName, BAD_SUBSCRIPTION_DATE);

        isExceptionThrown = false;
        Subscription subscription4 = new Subscription();
        subscription4.setLibrary(library);
        subscription4.setBorrower(borrower);
        subscription4.setContribution(10);
        subscription4.setStart(subscriptionStart3);
        subscription4.setEnd(subscriptionEnd3);
        try {
            subscriptionService.save(subscription4);
        } catch (Exception e) {
            isExceptionThrown = true;
        }
        Assert.assertFalse(isExceptionThrown);
    }
}
