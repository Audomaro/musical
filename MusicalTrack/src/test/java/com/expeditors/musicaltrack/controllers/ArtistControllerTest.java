package com.expeditors.musicaltrack.controllers;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.services.ArtistServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArtistServiceImpl artistService;

    @Test
    void getAll() throws Exception {

        List<Artist> artistList = List.of(
                new Artist(12, "Jonh A", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(22, "Jonh O", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(32, "Jonh V", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(42, "Jonh D", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true)
        );

        String jsonString = objectMapper.writeValueAsString(artistList);

        Mockito.when(artistService.getAll()).thenReturn(artistList);

        this.mockMvc.perform(
                        get("/artist")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString))
                .andDo(print());

        Mockito.verify(artistService).getAll();
    }

    @Test
    void getById() throws Exception {
        Artist artist = new Artist(12, "Jonh A", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true);

        Mockito.when(artistService.getById(12)).thenReturn(artist);

        this.mockMvc.perform(
                        get("/artist/" + artist.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(artist.getId()))
                .andExpect(jsonPath("$.firstName").value(artist.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(artist.getLastName()))
                .andDo(print());

        Mockito.verify(artistService).getById(artist.getId());
    }

    @Test
    void getByIdNotFound() throws Exception {
        int idArtistNotFound = 123;

        Mockito.when(artistService.getById(idArtistNotFound)).thenReturn(null);

        this.mockMvc.perform(
                        get("/artist/" + idArtistNotFound)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("No artist with id: " + idArtistNotFound))
                .andDo(print());

        Mockito.verify(artistService).getById(idArtistNotFound);
    }

    @Test
    void getByName() throws Exception {
        String artistName = "Jonh";

        List<Artist> artists = List.of(
                new Artist(12, "Jonh A", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(22, "Jonh O", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(32, "Jonh V", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(42, "Jonh D", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true)
        );

        String jsonString = objectMapper.writeValueAsString(artists);

        Mockito.when(artistService.getArtistByName(artistName)).thenReturn(artists);

        this.mockMvc.perform(
                        get("/artist/name/" + artistName)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(artists.size())))
                .andExpect(content().json(jsonString))
                .andDo(print());

        Mockito.verify(artistService).getArtistByName(artistName);
    }

    @Test
    void getByNameNotFound() throws Exception {
        String artistName = "Joe";

        Mockito.when(artistService.getArtistByName(artistName)).thenReturn(null);

        this.mockMvc.perform(
                        get("/artist/name/" + artistName)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("No artist with name: " + artistName))
                .andDo(print());

        Mockito.verify(artistService).getArtistByName(artistName);
    }

    @Test
    void postArtist() throws Exception {
        Artist artist = new Artist("Jonh A", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true);
        artist.setId(100);

        String artistJson = objectMapper.writeValueAsString(artist);

        Mockito.when(artistService.insert(artist)).thenReturn(artist);

        this.mockMvc.perform(
                        post("/artist")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(artistJson)
                )
                .andExpect(status().isCreated())
                .andDo(print());

        Mockito.verify(artistService).insert(artist);
    }

    @Test
    void putArtist() throws Exception {
        Artist artist = new Artist(100,"Jonh A", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true);
        String artistJson = objectMapper.writeValueAsString(artist);

        Mockito.when(artistService.update(artist.getId(), artist)).thenReturn(false);

        this.mockMvc.perform(
                        put("/artist")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(artistJson)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("No artist witg id: " + artist.getId() ))
                .andDo(print());

        Mockito.verify(artistService).update(artist.getId(), artist);
    }

    @Test
    void deleteArtist() throws Exception {
        int idArtistNotFound = 100;
        Mockito.when(artistService.delete(idArtistNotFound)).thenReturn(false);

        this.mockMvc.perform(
                        delete("/artist/" + idArtistNotFound)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("No artist with id: " + idArtistNotFound))
                .andDo(print());

        Mockito.verify(artistService).delete(idArtistNotFound);
    }

    @Test
    void getSummary() throws Exception {
        List<Artist> artists = List.of(
                new Artist(),
                new Artist(),
                new Artist(),
                new Artist(),
                new Artist()
        );

        Mockito.when(artistService.getAll()).thenReturn(artists);

        this.mockMvc.perform(
                        get("/artist/summary")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artists").value(artists.size()))
                .andDo(print());

        Mockito.verify(artistService).getAll();
    }
}