package org.lendi.umtapo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.dto.LoanDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.marc.transformer.impl.UnimarcToSimpleRecord;
import org.lendi.umtapo.service.configuration.Z3950Service;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.lendi.umtapo.service.specific.ItemService;
import org.lendi.umtapo.service.specific.LibraryService;
import org.lendi.umtapo.service.specific.LoanService;
import org.lendi.umtapo.service.specific.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import util.UtilCreator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Loan controller test.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LoanWebService.class)
@WithMockUser(username = "test", roles = {"USER", "ADMIN"})
public class LoanWebServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @MockBean
    private LibraryService libraryService;

    @MockBean
    private BorrowerService borrowerService;

    @MockBean
    private ItemService itemService;

    @MockBean
    private RecordService recordService;

    @MockBean
    private UnimarcToSimpleRecord unimarcToSimpleRecord;

    @MockBean
    private Z3950Service z3950Service;

    @Autowired
    private ObjectMapper objectMapper;

    private UtilCreator utilCreator;

    /**
     * Sets .
     */
    @Before
    public void setup() throws Exception {
        utilCreator = new UtilCreator();
    }

    /**
     * Test set borrower.
     *
     * @throws Exception the exception
     */
    @Test
    public void testPostLoan() throws Exception {
        LoanDto loanDto = new LoanDto();
        ItemDto itemDto = new ItemDto();
        BorrowerDto borrowerDto = new BorrowerDto();

        // General case, expect item.borrowed = true
        itemDto.setId(1);
        borrowerDto.setId(1);
        loanDto.setId(1);
        loanDto.setStart(utilCreator.getLoanStart());
        loanDto.setEnd(utilCreator.getLoanEnd());
        loanDto.setReturned(false);
        loanDto.setItem(itemDto);
        loanDto.setBorrower(borrowerDto);

        given(this.loanService.saveDto(any(LoanDto.class))).willReturn(loanDto);
        given(this.itemService.findOne(1)).willReturn(utilCreator.createItem(1, 1234));

        this.mockMvc.perform(post("/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loanService.saveDto(new LoanDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.returned", is(false)))
                .andExpect(jsonPath("$.start", notNullValue()))
                .andExpect(jsonPath("$.end", notNullValue()))
                .andExpect(jsonPath("$.item.id", is(1)))
                .andExpect(jsonPath("$.borrower.id", is(1)));
        verify(loanService, times(2)).saveDto(any(LoanDto.class));
        verifyNoMoreInteractions(loanService);
        Item item = itemService.findOne(1);
        Assert.assertEquals(true, item.getBorrowed());

        loanService.delete(1);
        itemDto.setBorrowed(false);
        itemService.saveDto(itemDto);

        // No borrower or library informations case
        loanDto.setItem(null);

        this.mockMvc.perform(post("/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loanService.saveDto(new LoanDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // No borrower id or library id case
        loanDto.setItem(itemDto);
        loanDto.setBorrower(new BorrowerDto());

        this.mockMvc.perform(post("/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loanService.saveDto(new LoanDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Item is already borrowed
        itemDto.setBorrowed(true);
        itemService.saveDto(itemDto);

        loanDto.setBorrower(borrowerDto);
        this.mockMvc.perform(post("/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loanService.saveDto(new LoanDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}
