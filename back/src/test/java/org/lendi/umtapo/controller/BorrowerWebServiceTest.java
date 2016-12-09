package org.lendi.umtapo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.mapper.BorrowerMapper;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Borrower controller test.
 * <p>
 * Created by axel on 04/10/16.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BorrowerWebService.class)
@WithMockUser(username = "test", roles = {"USER", "ADMIN"})
public class BorrowerWebServiceTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowerService borrowerService;

    @Autowired
    private ObjectMapper objectMapper;

    private DateTime dateTime;

    private BorrowerMapper borrowerMapper = new BorrowerMapper();

    private BorrowerDto borrowerDto = new BorrowerDto();

    private BorrowerDto borrowerDto2 = new BorrowerDto();

    @Before
    public void setup() {
        dateTime = new DateTime(2000, 1, 1, 0, 0, 0, 0);
        Date date = dateTime.toDate();
        Borrower borrower = new Borrower("NameTest", "CommentTest", date, 5,
                true, null, null, null, null);
        Borrower borrower2 = new Borrower("NameTest2", "CommentTest2", date, 7,
                false, null, null, null, null);
        borrowerDto = borrowerMapper.mapBorrowerToBorrowerDto(borrower);
        borrowerDto2 = borrowerMapper.mapBorrowerToBorrowerDto(borrower2);
    }

    @Test
    public void testGetBorrower() throws Exception {

        given(this.borrowerService.find(1)).willReturn(borrowerDto);
        String date1 = dateTime.toDate().toString();
        this.mockMvc.perform(get("/borrowers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("NameTest")))
                .andExpect(jsonPath("$.comment", is("CommentTest")))
//                .andExpect(jsonPath("$.birthday", is(dateTime)))
                .andExpect(jsonPath("$.quota", is(5)))
                .andExpect(jsonPath("$.emailOptin", is(true)))
                .andExpect(jsonPath("$.address", nullValue()))
                .andExpect(jsonPath("$.subscription", nullValue()))
                .andExpect(jsonPath("$.loan", nullValue()))
                .andExpect(jsonPath("$.library", nullValue()));
        verify(borrowerService, times(1)).find(1);
        verifyNoMoreInteractions(borrowerService);

    }

    @Test
    public void testGetBorrowers() throws Exception {

        List<BorrowerDto> borrowerDtos = Arrays.asList(borrowerDto, borrowerDto2);

        given(this.borrowerService.finds()).willReturn(borrowerDtos);

        this.mockMvc.perform(get("/borrowers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("NameTest")))
                .andExpect(jsonPath("$[0].comment", is("CommentTest")))
//                .andExpect(jsonPath("$.birthday", is(dateTime)))
                .andExpect(jsonPath("$[0].quota", is(5)))
                .andExpect(jsonPath("$[0].emailOptin", is(true)))
                .andExpect(jsonPath("$[0].address", nullValue()))
                .andExpect(jsonPath("$[0].subscription", nullValue()))
                .andExpect(jsonPath("$[0].loan", nullValue()))
                .andExpect(jsonPath("$[0].library", nullValue()))
                .andExpect(jsonPath("$[1].name", is("NameTest2")))
                .andExpect(jsonPath("$[1].comment", is("CommentTest2")))
//                .andExpect(jsonPath("$.birthday", is(dateTime)))
                .andExpect(jsonPath("$[1].quota", is(7)))
                .andExpect(jsonPath("$[1].emailOptin", is(false)))
                .andExpect(jsonPath("$[1].address", nullValue()))
                .andExpect(jsonPath("$[1].subscription", nullValue()))
                .andExpect(jsonPath("$[1].loan", nullValue()))
                .andExpect(jsonPath("$[1].library", nullValue()));
        verify(borrowerService, times(1)).finds();
        verifyNoMoreInteractions(borrowerService);
    }

    @Test
    public void testSetBorrower() throws Exception {

        given(this.borrowerService.setBorrower(any(BorrowerDto.class))).willReturn(borrowerDto);

        this.mockMvc.perform(post("/borrowers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(borrowerService.setBorrower(new BorrowerDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("NameTest")))
                .andExpect(jsonPath("$.comment", is("CommentTest")))
//                .andExpect(jsonPath("$.birthday", is(dateTime)))
                .andExpect(jsonPath("$.quota", is(5)))
                .andExpect(jsonPath("$.emailOptin", is(true)))
                .andExpect(jsonPath("$.address", nullValue()))
                .andExpect(jsonPath("$.subscription", nullValue()))
                .andExpect(jsonPath("$.loan", nullValue()))
                .andExpect(jsonPath("$.library", nullValue()));
        verify(borrowerService, times(2)).setBorrower(any());
        verifyNoMoreInteractions(borrowerService);
    }


}