package com.expeditors.musicalpricing.controllers;

import com.expeditors.musicalpricing.services.PricingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PricingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricingService pricingService;

    @Test
    void getPrice() throws Exception {
        int idTrack = 1;
        double price = 2.5;

        Mockito.when(pricingService.getPrice(idTrack)).thenReturn(price);

        this.mockMvc.perform(
                        get("/pricing/" + idTrack)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(pricingService).getPrice(idTrack);
    }
}