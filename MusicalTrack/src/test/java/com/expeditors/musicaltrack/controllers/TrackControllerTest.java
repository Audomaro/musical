package com.expeditors.musicaltrack.controllers;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.services.TrackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TrackControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrackService trackService;

    @Test
    void getAll() throws Exception {
        List<Track> tracks = List.of(
                new Track(1, "t", "t", List.of(1,2,3), LocalDate.of(2022,1,1), 600, com.expeditors.musicaltrack.domain.MediaType.mp3 ),
                new Track(1, "t", "t", List.of(1,2,3), LocalDate.of(2021,1,1), 600, com.expeditors.musicaltrack.domain.MediaType.mp3 ),
                new Track(1, "t", "t", List.of(1,2,3), LocalDate.of(2023,1,1), 600, com.expeditors.musicaltrack.domain.MediaType.mp3 ),
                new Track(1, "t", "t", List.of(1,2,3), LocalDate.of(2020,1,1), 600, com.expeditors.musicaltrack.domain.MediaType.mp3 )
        );

        String jsonString = objectMapper.writeValueAsString(tracks);

        Mockito.when(trackService.getAll()).thenReturn(tracks);

        this.mockMvc.perform(
                        get("/track")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString))
                .andDo(print());

        Mockito.verify(trackService).getAll();
    }

    @Test
    void getById() {
    }

    @Test
    void postArtist() {
    }

    @Test
    void updateArtist() {
    }

    @Test
    void deleteArtist() {
    }

    @Test
    void getByMediaType() {
    }

    @Test
    void getByYear() {
    }

    @Test
    void testGetByMediaType() {
    }

    @Test
    void getByDuration() {
    }

    @Test
    void getSummary() {
    }
}