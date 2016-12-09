package org.lendi.umtapo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lendi.umtapo.entity.Z3950;
import org.lendi.umtapo.entity.Z3950Configuration;
import org.lendi.umtapo.service.configuration.Z3950Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Library web service test.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(Z3950WebService.class)
@WithMockUser(username = "test", roles = {"USER", "ADMIN"})
public class Z3950WebServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Z3950Service z3950Service;

    @Autowired
    private ObjectMapper objectMapper;

    private Z3950Configuration z3950Configuration;

    @Before
    public void setup() {
        Map<Integer, Z3950> providers = new HashMap<>();
        Map<String, String> database = new HashMap<>();
        database.put("name", "TOUT_UTF8");
        database.put("password", "Z3950_BNF");
        database.put("username", "Z3950");
        Map<String, String> options = new HashMap<>();
        options.put("elementSetName", "f");
        Z3950 z3950 = new Z3950();
        z3950.setName("Bibliothèque Nationale de France");
        z3950.setUrl("z3950.bnf.fr");
        z3950.setPort(2211);
        z3950.setSyntax("1.2.840.10003.5.1");
        z3950.setDatabase(database);
        z3950.setOptions(options);
        providers.put(1, z3950);
        this.z3950Configuration.setProviders(providers);
    }

    @Test
    public void testGetZ3950() throws Exception {

        given(this.z3950Service.find(1)).willReturn(this.z3950Configuration.getProviders().get(1));
        this.mockMvc.perform(get("/libraries/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Bibliothèque Nationale de France")))
                .andExpect(jsonPath("$.url", is("z3950.bnf.fr")))
                .andExpect(jsonPath("$.port", is(2211)))
                .andExpect(jsonPath("$.syntax", is("1.2.840.10003.5.1")))
                .andExpect(jsonPath("$.database", is(this.z3950Configuration.getProviders().get(1).getDatabase())))
                .andExpect(jsonPath("$.options", is(this.z3950Configuration.getProviders().get(1).getOptions())));
        verify(z3950Service, times(1)).find(1);
        verifyNoMoreInteractions(z3950Service);
    }

    /*@Test
    public void testGetAllZ3950() throws Exception {
        List<Z3950> z3950List = Arrays.asList(this.z3950Configuration.getProviders().get(1));
        given(this.z3950Service.findAll()).willReturn(z3950List);

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
                .andExpect(jsonPath("$[0].borrowers", is(this.borrowers)))
                .andExpect(jsonPath("$[1].name", is("L'Îlot livres")))
                .andExpect(jsonPath("$[1].shelfMarkNb", is(3)))
                .andExpect(jsonPath("$[1].useDeweyClassification", is(false)))
                .andExpect(jsonPath("$[1].subscriptionDuration", is(365)))
                .andExpect(jsonPath("$[1].borrowDuration", is(15)))
                .andExpect(jsonPath("$[1].currency", is("€")))
                .andExpect(jsonPath("$[1].defaultZ3950", is(1)))
                .andExpect(jsonPath("$[1].borrowers", is(new ArrayList<>())));
        verify(libraryService, times(1)).findAll(true);
        verifyNoMoreInteractions(libraryService);
    }*/
}