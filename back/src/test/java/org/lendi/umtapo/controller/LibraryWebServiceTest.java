package org.lendi.umtapo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lendi.umtapo.dto.LibraryDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.entity.Library;
import org.lendi.umtapo.mapper.LibraryMapper;
import org.lendi.umtapo.service.specific.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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

    @Autowired
    private ObjectMapper objectMapper;

    private LibraryMapper libraryMapper = new LibraryMapper();
    private LibraryDto libraryDto1;
    private LibraryDto libraryDto2;
    private List<Borrower> borrowers = new ArrayList<>();

    /**
     * Sets .
     */
    @Before
    public void setup() {
        LocalDateTime localDateTime = LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0);
        Borrower borrower = new Borrower("NameTest", "CommentTest", localDateTime, 5,
                true, null, null, null, null);
        Borrower borrower2 = new Borrower("NameTest2", "CommentTest2", localDateTime, 7,
                false, null, null, null, null);
        this.borrowers.add(borrower);
        this.borrowers.add(borrower2);
        Library library1 = new Library("Library of tests", 3, true, 365, 30, "$", 1, this.borrowers);
        Library library2 = new Library("L'Îlot livres", 3, false, 365, 15, "€", 1, new ArrayList<>());
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
                .andExpect(jsonPath("$.name", is("Library of tests")))
                .andExpect(jsonPath("$.shelfMarkNb", is(3)))
                .andExpect(jsonPath("$.useDeweyClassification", is(true)))
                .andExpect(jsonPath("$.subscriptionDuration", is(365)))
                .andExpect(jsonPath("$.borrowDuration", is(30)))
                .andExpect(jsonPath("$.currency", is("$")))
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
    public void testGetLibraries() throws Exception {

        List<LibraryDto> libraryDtos = Arrays.asList(libraryDto1, libraryDto2);
        given(this.libraryService.findAllDto()).willReturn(libraryDtos);

        this.mockMvc.perform(get("/libraries")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Library of tests")))
                .andExpect(jsonPath("$[0].shelfMarkNb", is(3)))
                .andExpect(jsonPath("$[0].useDeweyClassification", is(true)))
                .andExpect(jsonPath("$[0].subscriptionDuration", is(365)))
                .andExpect(jsonPath("$[0].borrowDuration", is(30)))
                .andExpect(jsonPath("$[0].currency", is("$")))
                .andExpect(jsonPath("$[0].defaultZ3950", is(1)))
                .andExpect(jsonPath("$[1].name", is("L'Îlot livres")))
                .andExpect(jsonPath("$[1].shelfMarkNb", is(3)))
                .andExpect(jsonPath("$[1].useDeweyClassification", is(false)))
                .andExpect(jsonPath("$[1].subscriptionDuration", is(365)))
                .andExpect(jsonPath("$[1].borrowDuration", is(15)))
                .andExpect(jsonPath("$[1].currency", is("€")))
                .andExpect(jsonPath("$[1].defaultZ3950", is(1)));
        verify(libraryService, times(1)).findAllDto();
        verifyNoMoreInteractions(libraryService);
    }

    /**
     * Test set library.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSetLibrary() throws Exception {

        given(this.libraryService.saveDto(any(LibraryDto.class))).willReturn(libraryDto1);

        this.mockMvc.perform(post("/libraries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.libraryService.saveDto(new LibraryDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Library of tests")))
                .andExpect(jsonPath("$.shelfMarkNb", is(3)))
                .andExpect(jsonPath("$.useDeweyClassification", is(true)))
                .andExpect(jsonPath("$.subscriptionDuration", is(365)))
                .andExpect(jsonPath("$.borrowDuration", is(30)))
                .andExpect(jsonPath("$.currency", is("$")))
                .andExpect(jsonPath("$.defaultZ3950", is(1)));
        verify(libraryService, times(2)).saveDto(any());
        verifyNoMoreInteractions(libraryService);
    }
}