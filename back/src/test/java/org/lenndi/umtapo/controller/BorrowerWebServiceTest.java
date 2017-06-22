package org.lenndi.umtapo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lenndi.umtapo.dto.BorrowerDto;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.mapper.BorrowerMapper;
import org.lenndi.umtapo.marc.transformer.impl.UnimarcToSimpleRecord;
import org.lenndi.umtapo.service.configuration.Z3950Service;
import org.lenndi.umtapo.service.specific.BorrowerService;
import org.lenndi.umtapo.service.specific.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @MockBean
    private RecordService recordService;

    @MockBean
    private Z3950Service z3950Service;

    @MockBean
    private UnimarcToSimpleRecord unimarcToSimpleRecord;

    @Autowired
    private ObjectMapper objectMapper;

    private ZonedDateTime zonedDateTime;

    private BorrowerMapper borrowerMapper = new BorrowerMapper();

    private BorrowerDto borrowerDto = new BorrowerDto();

    private BorrowerDto borrowerDto2 = new BorrowerDto();

    /**
     * Sets .
     */
    @Before
    public void setup() {
        zonedDateTime = ZonedDateTime.of(2000, 5, 25, 21, 52, 35, 0, ZoneId.systemDefault());
        Borrower borrower = new Borrower("NameTest", "CommentTest", zonedDateTime, 5,
                true, null, null, null, "nfcId");
        Borrower borrower2 = new Borrower("NameTest2", "CommentTest2", zonedDateTime, 7,
                false, null, null, null, "nfcId");
        borrowerDto = borrowerMapper.mapBorrowerToBorrowerDto(borrower);
        borrowerDto2 = borrowerMapper.mapBorrowerToBorrowerDto(borrower2);
    }

    /**
     * Test get borrower.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetBorrower() throws Exception {

        given(this.borrowerService.findOneDto(1)).willReturn(borrowerDto);
        this.mockMvc.perform(get("/borrowers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("NameTest")))
                .andExpect(jsonPath("$.comment", is("CommentTest")))
                .andExpect(jsonPath("$.nfcId", is("nfcId")))
//                .andExpect(jsonPath("$.birthday", is(dateTime)))
                .andExpect(jsonPath("$.quota", is(5)))
                .andExpect(jsonPath("$.emailOptin", is(true)))
                .andExpect(jsonPath("$.address", nullValue()))
                .andExpect(jsonPath("$.subscriptions", nullValue()));
        verify(borrowerService, times(1)).findOneDto(1);
        verifyNoMoreInteractions(borrowerService);

    }

    /**
     * Test get borrowers.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetBorrowers() throws Exception {

        List<BorrowerDto> borrowerDtos = Arrays.asList(borrowerDto, borrowerDto2);
        Pageable pageable = new PageRequest(0, 100);
        Page<BorrowerDto> borrowerDtoPage = new PageImpl<BorrowerDto>(borrowerDtos, pageable, 2);

        given(this.borrowerService.findAllDto(pageable)).willReturn(borrowerDtoPage);

        this.mockMvc.perform(get("/borrowers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("NameTest")))
                .andExpect(jsonPath("$.content[0].nfcId", is("nfcId")))
//                .andExpect(jsonPath("$.birthday", is(dateTime)))
                .andExpect(jsonPath("$.content[0].quota", is(5)))
                .andExpect(jsonPath("$.content[0].emailOptin", is(true)))
                .andExpect(jsonPath("$.content[0].address", nullValue()))
                .andExpect(jsonPath("$.content[0].subscriptions", nullValue()))
                .andExpect(jsonPath("$.content[1].name", is("NameTest2")))
                .andExpect(jsonPath("$.content[1].comment", is("CommentTest2")))
//                .andExpect(jsonPath("$.birthday", is(dateTime)))
                .andExpect(jsonPath("$.content[1].nfcId", is("nfcId")))
                .andExpect(jsonPath("$.content[1].quota", is(7)))
                .andExpect(jsonPath("$.content[1].emailOptin", is(false)))
                .andExpect(jsonPath("$.content[1].address", nullValue()))
                .andExpect(jsonPath("$.content[1].subscriptions", nullValue()));
        verify(borrowerService, times(1)).findAllDto(pageable);
        verifyNoMoreInteractions(borrowerService);
    }

    /**
     * Test set borrower.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSetBorrower() throws Exception {

        given(this.borrowerService.saveDto(any(BorrowerDto.class))).willReturn(borrowerDto);

        this.mockMvc.perform(post("/borrowers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(borrowerService.saveDto(new BorrowerDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("NameTest")))
                .andExpect(jsonPath("$.comment", is("CommentTest")))
                .andExpect(jsonPath("$.nfcId", is("nfcId")))
//                .andExpect(jsonPath("$.birthday", is(dateTime)))
                .andExpect(jsonPath("$.quota", is(5)))
                .andExpect(jsonPath("$.emailOptin", is(true)))
                .andExpect(jsonPath("$.address", nullValue()))
                .andExpect(jsonPath("$.subscriptions", nullValue()));
        verify(borrowerService, times(2)).saveDto(any(BorrowerDto.class));
        verifyNoMoreInteractions(borrowerService);
    }

    @Test
    public void testUpdateBorrower() throws Exception {

        given(this.borrowerService.findOne(1)).willReturn(null);
        this.mockMvc.perform(put("/borrowers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.borrowerDto2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        this.borrowerDto.setId(2);
        given(this.borrowerService.saveDto(any(BorrowerDto.class))).willReturn(this.borrowerDto);
        given(this.borrowerService.findOne(2)).willReturn(this.borrowerMapper.mapBorrowerDtoToBorrower(this.borrowerDto));
        this.mockMvc.perform(put("/borrowers/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.borrowerDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.name", is("NameTest")))
                .andExpect(jsonPath("$.comment", is("CommentTest")))
                .andExpect(jsonPath("$.quota", is(5)))
                .andExpect(jsonPath("$.emailOptin", is(true)))
                .andExpect(jsonPath("$.address", nullValue()))
                .andExpect(jsonPath("$.subscriptions", nullValue()));
    }

    @Test
    public void testDeleteBorrower() throws Exception {

        this.mockMvc.perform(delete("/borrowers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
