package org.lendi.umtapo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.mapper.ItemMapper;
import org.lendi.umtapo.service.specific.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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

    @Autowired
    private ObjectMapper objectMapper;

    private ZonedDateTime zonedDateTime;

    private ItemMapper itemMapper = new ItemMapper();

    private ItemDto itemDto = new ItemDto();

    private ItemDto itemDto2 = new ItemDto();

    /**
     * Sets .
     */
    @Before
    public void setup() {
        Item item = new Item("typeTest", "titleTest", "internalIdTest", 5, true, null, null);
        Item item2 = new Item("typeTest2", "titleTest2", "internalIdTest2", 7, false, null, null);
        itemDto = itemMapper.mapItemToItemDto(item);
        itemDto2 = itemMapper.mapItemToItemDto(item2);
    }

    /**
     * Test get item.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetItem() throws Exception {

        given(this.itemService.findOneDto(1)).willReturn(itemDto);
        this.mockMvc.perform(get("/items/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("typeTest")))
                .andExpect(jsonPath("$.title", is("titleTest")))
                .andExpect(jsonPath("$.internalId", is("internalIdTest")))
                .andExpect(jsonPath("$.purchasePrice", is(5)))
                .andExpect(jsonPath("$.loanable", is(true)))
                .andExpect(jsonPath("$.loan", nullValue()))
                .andExpect(jsonPath("$.condition", nullValue()));
        verify(itemService, times(1)).findOneDto(1);
        verifyNoMoreInteractions(itemService);

    }

    /**
     * Test get items.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetItems() throws Exception {

        List<ItemDto> itemDtos = Arrays.asList(itemDto, itemDto2);

        given(this.itemService.findAllDto()).willReturn(itemDtos);

        this.mockMvc.perform(get("/items")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("typeTest")))
                .andExpect(jsonPath("$[0].title", is("titleTest")))
                .andExpect(jsonPath("$[0].internalId", is("internalIdTest")))
                .andExpect(jsonPath("$[0].purchasePrice", is(5)))
                .andExpect(jsonPath("$[0].loanable", is(true)))
                .andExpect(jsonPath("$[0].loan", nullValue()))
                .andExpect(jsonPath("$[0].condition", nullValue()))
                .andExpect(jsonPath("$[1].type", is("typeTest2")))
                .andExpect(jsonPath("$[1].title", is("titleTest2")))
                .andExpect(jsonPath("$[1].internalId", is("internalIdTest2")))
                .andExpect(jsonPath("$[1].purchasePrice", is(7)))
                .andExpect(jsonPath("$[1].loanable", is(false)))
                .andExpect(jsonPath("$[1].loan", nullValue()))
                .andExpect(jsonPath("$[1].condition", nullValue()));
        verify(itemService, times(1)).findAllDto();
        verifyNoMoreInteractions(itemService);
    }

    /**
     * Test set item.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSetItem() throws Exception {

        given(this.itemService.saveDto(any(ItemDto.class))).willReturn(itemDto);

        this.mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(itemService.saveDto(new ItemDto())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type", is("typeTest")))
                .andExpect(jsonPath("$.title", is("titleTest")))
                .andExpect(jsonPath("$.internalId", is("internalIdTest")))
                .andExpect(jsonPath("$.purchasePrice", is(5)))
                .andExpect(jsonPath("$.loanable", is(true)))
                .andExpect(jsonPath("$.loan", nullValue()))
                .andExpect(jsonPath("$.condition", nullValue()));
        verify(itemService, times(2)).saveDto(any(ItemDto.class));
        verifyNoMoreInteractions(itemService);
    }
}
