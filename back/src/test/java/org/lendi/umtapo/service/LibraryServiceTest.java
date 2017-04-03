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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UmtapoApplication.class)
@ContextConfiguration(classes = SolrTestConfig.class)
@Sql(value = "classpath:test/truncate.sql")
public class LibraryServiceTest {

    @Autowired
    private LibraryDao libraryDao;
    @Autowired
    private LibraryService libraryService;
    private UtilCreator utilCreator;

    public LibraryServiceTest() {
        this.utilCreator = new UtilCreator();
    }

    @Before
    public void setup() {}

    @After
    public void tearDown(){}

    @Test
    public void testSaveDto() throws Exception {
        LibraryDto libraryDto = this.utilCreator.createLibraryDto(1, false);

        this.libraryService.saveDto(libraryDto);
        Library savedLibrary = this.libraryDao.findOne(1);

        Assert.assertNotNull(savedLibrary);
        Assert.assertEquals(1, savedLibrary.getId().longValue());
    }

    @Test
    public void testFindAllDto() throws Exception {
        Library library = this.utilCreator.createLibrary(1, "library", false);
        Library library1 = this.utilCreator.createLibrary(2, "library1", true);

        this.libraryService.save(library);
        this.libraryService.save(library1);

        List<LibraryDto> libraryDto = this.libraryService.findAllDto();

        Assert.assertEquals(2, libraryDto.size());
    }

    @Test
    public void testFindAllPartner() throws Exception {
        Library library = this.utilCreator.createLibrary(1, "library", false);
        Library library1 = this.utilCreator.createLibrary(2, "library1", true);
        Library library2 = this.utilCreator.createLibrary(3, "library2", false);
        Library library3 = this.utilCreator.createLibrary(4, "library3", true);

        this.libraryService.save(library);
        this.libraryService.save(library1);
        this.libraryService.save(library2);
        this.libraryService.save(library3);

        List<LibraryDto> libraryDto = this.libraryService.findAllExternal();

        Assert.assertEquals(2, libraryDto.size());
    }

    @Test
    public void testFindAllExternal() throws Exception {
        Library library = this.utilCreator.createLibrary(1, "library", false);
        Library library1 = this.utilCreator.createLibrary(2, "library1", true);
        Library library2 = this.utilCreator.createLibrary(3, "library2", false);
        Library library3 = this.utilCreator.createLibrary(4, "library3", true);

        this.libraryService.save(library);
        this.libraryService.save(library1);
        this.libraryService.save(library2);
        this.libraryService.save(library3);

        List<LibraryDto> libraryDto = this.libraryService.findAllPartner();

        Assert.assertEquals(2, libraryDto.size());
    }
}
