package org.lenndi.umtapo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lenndi.umtapo.dto.ItemDto;
import org.lenndi.umtapo.marc.transformer.impl.UnimarcToSimpleRecord;
import org.lenndi.umtapo.service.configuration.Z3950Service;
import org.lenndi.umtapo.service.specific.ItemService;
import org.lenndi.umtapo.service.specific.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import util.UtilCreator;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * BorrowerWebservice Test.
 * <p>
 * Created by axel on 10/01/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ItemWebService.class)
@WithMockUser(username = "test", roles = {"USER", "ADMIN"})
public class ItemWebServiceTest {

    @Autowired
    private MockMvc mockMvc;

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
    public void setup() {
        utilCreator = new UtilCreator();
    }

    /**
     * Test get item.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetItem() throws Exception {

        given(this.itemService.findOneDto(1)).willReturn(utilCreator.createItemDto(1, 12345));
        this.mockMvc.perform(get("/items/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("BOOK")))
                .andExpect(jsonPath("$.internalId", is(12345)))
                .andExpect(jsonPath("$.purchasePrice", is(6.0)))
                .andExpect(jsonPath("$.loanable", is(true)))
                .andExpect(jsonPath("$.condition", is("NEW")));
        verify(itemService, times(1)).findOneDto(1);
        verifyNoMoreInteractions(itemService);
    }


    @Test
    public void testGetItems() throws Exception {

        List<ItemDto> itemDtos = Arrays.asList(utilCreator.createItemDto(1, 1234), utilCreator.createItemDto(2, 1235));
        Page<ItemDto> itemPage = new PageImpl<>(itemDtos);

        given(itemService.findAllPageableDto(any(Pageable.class))).willReturn(itemPage);

        this.mockMvc.perform(get("/items?page=0&size=10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].type", is("BOOK")))
                .andExpect(jsonPath("$.content[0].internalId", is(1234)))
                .andExpect(jsonPath("$.content[0].purchasePrice", is(6.0)))
                .andExpect(jsonPath("$.content[0].loanable", is(true)))
                .andExpect(jsonPath("$.content[0].condition", is("NEW")))
                .andExpect(jsonPath("$.content[1].type", is("BOOK")))
                .andExpect(jsonPath("$.content[1].internalId", is(1235)))
                .andExpect(jsonPath("$.content[1].purchasePrice", is(6.0)))
                .andExpect(jsonPath("$.content[1].loanable", is(true)))
                .andExpect(jsonPath("$.content[1].condition", is("NEW")));
        verify(itemService, times(1)).findAllPageableDto(any(Pageable.class));
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void testSetItem() throws Exception {
        given(this.itemService.saveDto(any(ItemDto.class))).willReturn(utilCreator.createItemDto(1, 1234));

        this.mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(itemService.saveDto(new ItemDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type", is("BOOK")))
                .andExpect(jsonPath("$.internalId", is(1234)))
                .andExpect(jsonPath("$.purchasePrice", is(6.0)))
                .andExpect(jsonPath("$.loanable", is(true)))
                .andExpect(jsonPath("$.condition", is("NEW")));
        verify(itemService, times(2)).saveDto(any(ItemDto.class));
        verifyNoMoreInteractions(itemService);
    }
}
