package org.lenndi.umtapo.solr.service.implementation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lenndi.umtapo.UmtapoApplication;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.solr.SolrTestConfig;
import org.lenndi.umtapo.solr.document.BorrowerDocument;
import org.lenndi.umtapo.solr.repository.SolrBorrowerRepository;
import org.lenndi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import util.UtilCreator;

import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UmtapoApplication.class)
@ContextConfiguration(classes = SolrTestConfig.class)
public class SolrBorrowerServiceTest {

    @Autowired
    private SolrBorrowerService solrBorrowerService;
    @Autowired
    private SolrBorrowerRepository solrBorrowerRepository;

    private UtilCreator utilCreator;

    public SolrBorrowerServiceTest() {
        this.utilCreator = new UtilCreator();
    }

    @Before
    public void setup() {
        solrBorrowerRepository.deleteAll();
    }

    @Test
    public void testSaveBorrower() throws Exception {
        Borrower borrower = this.utilCreator.createBorrower(1);

        solrBorrowerService.saveToIndex(borrower);
        BorrowerDocument savedBorrowerDocument = solrBorrowerService.findById(borrower.getId().toString());

        Assert.assertEquals(savedBorrowerDocument.getId(), "1");
        Assert.assertEquals(savedBorrowerDocument.getName(), "Michel Test");
        Assert.assertEquals(savedBorrowerDocument.getComment(), "Un bien bel usager.");
        Assert.assertEquals(savedBorrowerDocument.getBirthday(), Date.from(this.utilCreator.getBirthday().toInstant()));
        Assert.assertEquals(savedBorrowerDocument.getQuota().longValue(), 5);
        Assert.assertEquals(savedBorrowerDocument.getAddressId(), "3");
        Assert.assertEquals(savedBorrowerDocument.getAddress1(), "3 rue des poules");
        Assert.assertEquals(savedBorrowerDocument.getAddress2(), "Étage 5");
        Assert.assertEquals(savedBorrowerDocument.getZip(), "35000");
        Assert.assertEquals(savedBorrowerDocument.getCity(), "Rennes");
        Assert.assertEquals(savedBorrowerDocument.getPhone(), "0299813994");
        Assert.assertEquals(savedBorrowerDocument.getEmail(), "michel@test.com");
    }

    @Test
    public void testSearchByNameOrEmail() throws Exception {
        BorrowerDocument borrowerDocument = this.utilCreator.createBorrowerDocument("1", "Michel Test", "michel@test.com");
        BorrowerDocument borrowerDocument1 = this.utilCreator.createBorrowerDocument("2", "Micheline Test", "micheline@test.com");
        BorrowerDocument borrowerDocument2 = this.utilCreator.createBorrowerDocument("3", "Francis Dupond", "francis@dupond.com");

        solrBorrowerService.saveToIndex(borrowerDocument);
        solrBorrowerService.saveToIndex(borrowerDocument1);
        solrBorrowerService.saveToIndex(borrowerDocument2);

        Pageable page = new PageRequest(0, 10);

        Assert.assertEquals(2, solrBorrowerService.searchByNameOrEmail("test", page).getNumberOfElements());
        Assert.assertEquals(0, solrBorrowerService.searchByNameOrEmail("yop", page).getNumberOfElements());
    }

    @Test
    public void testUpdate() throws Exception {
        BorrowerDocument borrowerDocument = this.utilCreator.createBorrowerDocument("1", "Michel Test", "michel@test.com");
        this.solrBorrowerRepository.save(borrowerDocument);

        borrowerDocument.setName("Gudule");
        borrowerDocument.setTooMuchLoans(null);

        solrBorrowerRepository.update(borrowerDocument);
        BorrowerDocument updatedBorrower = solrBorrowerService.findById("1");

        Assert.assertEquals("Gudule", updatedBorrower.getName());
        Assert.assertNotNull(updatedBorrower.getTooMuchLoans());
    }
}
