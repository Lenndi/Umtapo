package org.lendi.umtapo.controller;


import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lendi.umtapo.configuration.security.SecurityConfiguration;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.mapper.BorrowerMapper;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Borrower controller test.
 * <p>
 * Created by axel on 04/10/16.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BorrowerWebService.class)
public class BorrowerWebServiceTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowerService borrowerService;

    private BorrowerMapper borrowerMapper = new BorrowerMapper();

    private BorrowerDto borrowerDto = new BorrowerDto();


    @Before
    public void setup() {
        DateTime dateTime = new DateTime(2000, 1, 1, 0, 0, 0, 0);
        Borrower borrower = new Borrower("NameTest", "CommentTest", dateTime, 5,
                true, null, null, null, null);
        borrowerDto = borrowerMapper.mapBorrowerToBorrowerDto(borrower);

    }

    @Test
    public void testGetBorrower() throws Exception {

        given(this.borrowerService.find(1)).willReturn(borrowerDto);

        this.mockMvc.perform(get("/borrowers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("NameTest")))
                .andExpect(jsonPath("$.comment", is("CommentTest")))
                .andExpect(jsonPath("$.birthday", is(new DateTime(2000, 1, 1, 0, 0, 0, 0))))
                .andExpect(jsonPath("$.quota", is("5")))
                .andExpect(jsonPath("$.emailOptin", is(true)))
                .andExpect(jsonPath("$.address", nullValue()))
                .andExpect(jsonPath("$.subscription", nullValue()))
                .andExpect(jsonPath("$.loan", nullValue()))
                .andExpect(jsonPath("$.library", nullValue()));
        verify(borrowerService, times(1)).find(1);
        verifyNoMoreInteractions(borrowerService);

    }

//    @Test
//    public void testGetBorrowers() throws Exception {
//
//        List<Borrower> borrowers = Arrays.asList(
//                new Borrower("CaptainCarot", "125", "8"),
//                new Borrower("CaptainNavet", "78", "5"));
//
//        given(this.borrowerService.getBorrowers()).willReturn(borrowers);
//
//        this.mockMvc.perform(get("/borrower")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is("CaptainCarot")))
//                .andExpect(jsonPath("$[0].height", is("125")))
//                .andExpect(jsonPath("$[0].rank", is("8")))
//                .andExpect(jsonPath("$[1].name", is("CaptainNavet")))
//                .andExpect(jsonPath("$[1].height", is("78")))
//                .andExpect(jsonPath("$[1].rank", is("5")));
//        verify(borrowerService, times(1)).getBorrowers();
//        verifyNoMoreInteractions(borrowerService);
//    }
//
//    @Test
//    public void testSetBorrower() throws Exception {
//        given(this.borrowerService.setBorrower(any(Borrower.class))).willReturn(new Borrower
//                ("MajorZerg", "160", "3"));
//
//        this.mockMvc.perform(post("/borrower")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(borrowerService.setBorrower(new Borrower())))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name", is("MajorZerg")))
//                .andExpect(jsonPath("$.height", is("160")))
//                .andExpect(jsonPath("$.rank", is("3")));
//        verify(borrowerService, times(2)).setBorrower(any());
//        verifyNoMoreInteractions(borrowerService);
//    }
//
//    @Test
//    public void testUpdateBorrower() throws Exception {
//
//        given(this.borrowerService.updateBorrower(any(Borrower.class))).willReturn(new Borrower
//                ("MajorZerg", "160", "3"));
//
//        this.mockMvc.perform(put("/borrower")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(borrowerService.updateBorrower(new Borrower())))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name", is("MajorZerg")))
//                .andExpect(jsonPath("$.height", is("160")))
//                .andExpect(jsonPath("$.rank", is("3")));
//        verify(borrowerService, times(2)).updateBorrower(any());
//        verifyNoMoreInteractions(borrowerService);
//    }
//
//    @Test
//    public void testDeleteBorrower() throws Exception {
//
//        mockMvc.perform(delete("/borrower/{id}", 1)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//        verify(borrowerService, times(1)).deleteBorrower(any());
//        verifyNoMoreInteractions(borrowerService);
//    }
}