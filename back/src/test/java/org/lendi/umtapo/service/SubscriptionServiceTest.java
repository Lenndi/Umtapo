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
import org.lendi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import util.UtilCreator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UmtapoApplication.class)
@ContextConfiguration(classes = SolrTestConfig.class)
public class SubscriptionServiceTest {

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
    private UtilCreator utilCreator;
    private Borrower borrower;
    private Library library;

    @Before
    public void setup() throws Exception {
        this.utilCreator = new UtilCreator();
        solrBorrowerService.deleteFromIndex(1);
        BorrowerDocument borrowerDocument = new BorrowerDocument();
        borrowerDocument.setId("1");
        solrBorrowerService.saveToIndex(borrowerDocument);
        library = utilCreator.createLibrary(1);
        borrower = utilCreator.createBorrower(1);
        library = libraryDao.save(library);
        borrower = borrowerDao.save(borrower);

    }

    @After
    public void tearDown() throws Exception {
        this.subscriptionDao.deleteAll();
        libraryDao.deleteAll();
        borrowerDao.deleteAll();
    }

    @Test
    public void testSave() throws Exception {
        Subscription subscription = utilCreator.createSubscription(1, borrower, library);
        subscriptionService.save(subscription);

        BorrowerDocument borrowerDocument1 = solrBorrowerService.findById("1");
        Assert.assertEquals(subscription.getEnd(), borrowerDocument1.getSubscriptionEnd());
        Assert.assertEquals(subscription.getStart(), borrowerDocument1.getSubscriptionStart());
    }

    @Test
    public void testSaveDto() throws Exception {
        SubscriptionDto subscriptionDto = subscriptionService.saveDto(utilCreator.createSubscriptionDto(1, utilCreator.createBorrowerDto(borrower.getId()), utilCreator.createLibraryDto(library.getId())));

        Subscription subscription = subscriptionDao.findOne(subscriptionDto.getId());

        Assert.assertNotNull(subscription);
        Assert.assertEquals(subscription.getContribution().longValue(), 650);
        Assert.assertEquals(subscription.getStart(), utilCreator.getSubscriptionStart());
        Assert.assertEquals(subscription.getEnd(), utilCreator.getSubscriptionEnd());
        Assert.assertEquals(subscription.getLibrary().getId(), library.getId());
        Assert.assertEquals(subscription.getBorrower().getId(), borrower.getId());
    }
}
