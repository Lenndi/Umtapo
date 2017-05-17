package org.lenndi.umtapo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lenndi.umtapo.dto.LibraryDto;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.entity.Library;
import org.lenndi.umtapo.mapper.LibraryMapper;
import org.lenndi.umtapo.marc.transformer.impl.UnimarcToSimpleRecord;
import org.lenndi.umtapo.service.configuration.Z3950Service;
import org.lenndi.umtapo.service.specific.ItemService;
import org.lenndi.umtapo.service.specific.LibraryService;
import org.lenndi.umtapo.service.specific.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import util.UtilCreator;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Library web service test.
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringRunner.class)
@WebMvcTest(LibraryWebService.class)
@WithMockUser(username = "test", roles = {"USER", "ADMIN"})
public class LibraryWebServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @MockBean
    private ItemService itemService;

    @MockBean
    private RecordService recordService;

    @MockBean
    private Z3950Service z3950Service;

    @MockBean
    private UnimarcToSimpleRecord unimarcToSimpleRecord;

    @Autowired
    private ObjectMapper objectMapper;

    private UtilCreator utilCreator;
    private LibraryMapper libraryMapper = new LibraryMapper();
    private LibraryDto libraryDto1;
    private LibraryDto libraryDto2;
    private List<Borrower> borrowers = new ArrayList<>();

    /**
     * Sets .
     */
    @Before
    public void setup() {
        utilCreator = new UtilCreator();

        ZonedDateTime zonedDateTime = java.time.ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        Borrower borrower = new Borrower("NameTest", "CommentTest", zonedDateTime, 5,
                true, null, null, null);
        Borrower borrower2 = new Borrower("NameTest2", "CommentTest2", zonedDateTime, 7,
                false, null, null, null);
        this.borrowers.add(borrower);
        this.borrowers.add(borrower2);
        Library library1 = utilCreator.createLibrary(1, false);
        Library library2 = utilCreator.createLibrary(2, true);
        this.libraryDto1 = this.libraryMapper.mapLibraryToLibraryDto(library1);
        this.libraryDto2 = this.libraryMapper.mapLibraryToLibraryDto(library2);
    }

    /**
     * Test get library.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetLibrary() throws Exception {

        given(this.libraryService.findOneDto(1)).willReturn(this.libraryDto1);
        this.mockMvc.perform(get("/libraries/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Library")))
                .andExpect(jsonPath("$.shelfMarkNb", is(3)))
                .andExpect(jsonPath("$.useDeweyClassification", is(false)))
                .andExpect(jsonPath("$.subscriptionDuration", is(365)))
                .andExpect(jsonPath("$.borrowDuration", is(30)))
                .andExpect(jsonPath("$.currency", is("€")))
                .andExpect(jsonPath("$.defaultZ3950", is(1)));
        verify(libraryService, times(1)).findOneDto(1);
        verifyNoMoreInteractions(libraryService);
    }

    /**
     * Test get libraries.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetPartnerLibraries() throws Exception {

        List<LibraryDto> libraryDtos = Arrays.asList(utilCreator.createLibraryDto(1, false));
        given(this.libraryService.findAllPartner()).willReturn(libraryDtos);

        this.mockMvc.perform(get("/libraries?external=false")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Test Library")))
                .andExpect(jsonPath("$[0].shelfMarkNb", is(3)))
                .andExpect(jsonPath("$[0].useDeweyClassification", is(false)))
                .andExpect(jsonPath("$[0].subscriptionDuration", is(365)))
                .andExpect(jsonPath("$[0].borrowDuration", is(30)))
                .andExpect(jsonPath("$[0].currency", is("€")))
                .andExpect(jsonPath("$[0].defaultZ3950", is(1)));
        verify(libraryService, times(1)).findAllPartner();
        verifyNoMoreInteractions(libraryService);
    }

    /**
     * Test get libraries.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetExternalLibraries() throws Exception {

        List<LibraryDto> libraryDtos = Arrays.asList(utilCreator.createLibraryDto(1, true));
        given(this.libraryService.findAllExternal()).willReturn(libraryDtos);

        this.mockMvc.perform(get("/libraries?external=true")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Test Library")))
                .andExpect(jsonPath("$[0].shelfMarkNb", is(3)))
                .andExpect(jsonPath("$[0].useDeweyClassification", is(false)))
                .andExpect(jsonPath("$[0].subscriptionDuration", is(365)))
                .andExpect(jsonPath("$[0].borrowDuration", is(30)))
                .andExpect(jsonPath("$[0].currency", is("€")))
                .andExpect(jsonPath("$[0].defaultZ3950", is(1)))
                .andExpect(jsonPath("$[0].external", is(true)));
        verify(libraryService, times(1)).findAllExternal();
        verifyNoMoreInteractions(libraryService);
    }

    /**
     * Test set library.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSetPartnerLibrary() throws Exception {
        LibraryDto libraryDto = utilCreator.createLibraryDto(1, false);

        given(this.libraryService.createLibrary(any(LibraryDto.class))).willReturn(libraryDto);
        given(this.itemService.save(any(Item.class))).willReturn(utilCreator.createItem(1, libraryDto.getFirstInternalId()));

        this.mockMvc.perform(post("/libraries/partner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.libraryService.createLibrary(new LibraryDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Test Library")))
                .andExpect(jsonPath("$.shelfMarkNb", is(3)))
                .andExpect(jsonPath("$.useDeweyClassification", is(false)))
                .andExpect(jsonPath("$.subscriptionDuration", is(365)))
                .andExpect(jsonPath("$.borrowDuration", is(30)))
                .andExpect(jsonPath("$.currency", is("€")))
                .andExpect(jsonPath("$.defaultZ3950", is(1)))
                .andExpect(jsonPath("$.firstInternalId", is(1234)))
                .andExpect(jsonPath("$.external", is(false)));
        verify(libraryService, times(2)).createLibrary(any());
    }

    /**
     * Test set library.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSetExternalLibrary() throws Exception {
        LibraryDto libraryDto = utilCreator.createLibraryDto(1, true);

        given(this.libraryService.saveDto(any(LibraryDto.class))).willReturn(libraryDto);
        given(this.itemService.save(any(Item.class))).willReturn(utilCreator.createItem(1, libraryDto.getFirstInternalId()));

        this.mockMvc.perform(post("/libraries/external")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.libraryService.saveDto(new LibraryDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Test Library")))
                .andExpect(jsonPath("$.shelfMarkNb", is(3)))
                .andExpect(jsonPath("$.useDeweyClassification", is(false)))
                .andExpect(jsonPath("$.subscriptionDuration", is(365)))
                .andExpect(jsonPath("$.borrowDuration", is(30)))
                .andExpect(jsonPath("$.currency", is("€")))
                .andExpect(jsonPath("$.defaultZ3950", is(1)))
                .andExpect(jsonPath("$.firstInternalId", is(1234)))
                .andExpect(jsonPath("$.external", is(true)));
        verify(libraryService, times(2)).saveDto(any());
    }
}