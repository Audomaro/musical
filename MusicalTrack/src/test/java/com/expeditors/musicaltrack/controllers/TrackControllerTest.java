package com.expeditors.musicaltrack.controllers;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.domain.DurationTrack;
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
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
                new Track(1, "Song A", "Album A", List.of(1, 2), LocalDate.now(), 300, com.expeditors.musicaltrack.domain.MediaType.mp3,0),
                new Track(2, "Song B", "Album B", List.of(1, 3), LocalDate.now(), 300, com.expeditors.musicaltrack.domain.MediaType.mp3,0),
                new Track(3, "Song C", "Album C", List.of(2, 3), LocalDate.now(), 300, com.expeditors.musicaltrack.domain.MediaType.mp3,0)
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
    void getById() throws Exception {
        Track track = new Track(1, "Song A", "Album A", List.of(1,2,3), LocalDate.now(), 300, com.expeditors.musicaltrack.domain.MediaType.mp3,0);

        Mockito.when(trackService.getById(1)).thenReturn(track);

        this.mockMvc.perform(
                        get("/track/" + track.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(track.getId()))
                .andExpect(jsonPath("$.title").value(track.getTitle()))
                .andExpect(jsonPath("$.album").value(track.getAlbum()))
                .andDo(print());

        Mockito.verify(trackService).getById(track.getId());
    }

    @Test
    void getByIdNotFound() throws Exception {

        int idTrackNotFound = 999;

        Mockito.when(trackService.getById(1)).thenReturn(null);

        this.mockMvc.perform(
                        get("/track/" + 999)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("No track with id: " + idTrackNotFound))
                .andDo(print());

        Mockito.verify(trackService).getById(idTrackNotFound);
    }

    @Test
    void postArtist() throws Exception {
        Track track = new Track("Song A", "Album A", List.of(1,2,3), LocalDate.now(), 300, com.expeditors.musicaltrack.domain.MediaType.mp3);

        track.setId(33);

        String trackJson = objectMapper.writeValueAsString(track);

        Mockito.when(trackService.insert(track)).thenReturn(track);

        this.mockMvc.perform(
                        post("/track")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(trackJson)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/track/" + track.getId()))
                .andDo(print());

        Mockito.verify(trackService).insert(track);
    }

    @Test
    void updateArtist() throws Exception {
        Track track = new Track(35,"Song A", "Album A", List.of(1,2,3), LocalDate.now(), 300, com.expeditors.musicaltrack.domain.MediaType.mp3,0);
        String trackJson = objectMapper.writeValueAsString(track);

        Mockito.when(trackService.update(track.getId(), track)).thenReturn(true);

        this.mockMvc.perform(
                        put("/track")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(trackJson)
                )
                .andExpect(status().isNoContent())
                .andDo(print());

        Mockito.verify(trackService).update(track.getId(), track);
    }

    @Test
    void updateArtistNotFound() throws Exception {
        Track track = new Track(35,"Song A", "Album A", List.of(1,2,3), LocalDate.now(), 300, com.expeditors.musicaltrack.domain.MediaType.mp3,0);
        String trackJson = objectMapper.writeValueAsString(track);

        Mockito.when(trackService.update(track.getId(), track)).thenReturn(false);

        this.mockMvc.perform(
                        put("/track")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(trackJson)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("No track with id: " + track.getId()))
                .andDo(print());

        Mockito.verify(trackService).update(track.getId(), track);
    }

    @Test
    void deleteArtist() throws Exception {
        int idTack = 100;
        Mockito.when(trackService.delete(idTack)).thenReturn(true);

        this.mockMvc.perform(
                        delete("/track/" + idTack)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(print());

        Mockito.verify(trackService).delete(idTack);
    }


    @Test
    void deleteArtistNotFound() throws Exception {
        int idTackNotFound = 100;
        Mockito.when(trackService.delete(idTackNotFound)).thenReturn(false);

        this.mockMvc.perform(
                        delete("/track/" + idTackNotFound)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("No track with id: " + idTackNotFound))
                .andDo(print());

        Mockito.verify(trackService).delete(idTackNotFound);
    }

    @Test
    void getByMediaType() throws Exception {
        com.expeditors.musicaltrack.domain.MediaType mediaType = com.expeditors.musicaltrack.domain.MediaType.mp3;

        List<Track> tracks = List.of(
                new Track(1, "Song A", "Album A", List.of(1,2,3), LocalDate.of(2022,1,1), 600, mediaType,0),
                new Track(2, "Song B", "Album B", List.of(1,2,3), LocalDate.of(2021,1,1), 600, mediaType,0),
                new Track(3, "Song C", "Album C", List.of(1,2,3), LocalDate.of(2023,1,1), 600, mediaType,0),
                new Track(4, "Song D", "Album D", List.of(1,2,3), LocalDate.of(2023,1,1), 600, mediaType,0)
        );

        String jsonString = objectMapper.writeValueAsString(tracks);

        Mockito.when(trackService.getTrackByMediaType(mediaType)).thenReturn(tracks);

        this.mockMvc.perform(
                        get("/track/media-type/" + mediaType)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString))
                .andDo(print());

        Mockito.verify(trackService).getTrackByMediaType(mediaType);
    }

    @Test
    void getByYear() throws Exception {
        int year = 2022;

        List<Track> tracks = List.of(
                new Track(1, "Song A", "Album A", List.of(1,2,3), LocalDate.of(year,1,1), 600, com.expeditors.musicaltrack.domain.MediaType.mp3,0),
                new Track(2, "Song B", "Album B", List.of(1,2,3), LocalDate.of(year,1,1), 600, com.expeditors.musicaltrack.domain.MediaType.ogg ,0),
                new Track(3, "Song C", "Album C", List.of(1,2,3), LocalDate.of(year,1,1), 600, com.expeditors.musicaltrack.domain.MediaType.wav,0),
                new Track(4, "Song D", "Album D", List.of(1,2,3), LocalDate.of(year,1,1), 600, com.expeditors.musicaltrack.domain.MediaType.flac,0 )
        );

        String jsonString = objectMapper.writeValueAsString(tracks);

        Mockito.when(trackService.getTrackByYear(year)).thenReturn(tracks);

        this.mockMvc.perform(
                        get("/track/year/" + year)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString))
                .andDo(print());

        Mockito.verify(trackService).getTrackByYear(year);
    }

    @Test
    void getByDuration() throws Exception {
        DurationTrack durationTrack = DurationTrack.shorted;
        int seconds = 200;

        List<Track> tracks = List.of(
                new Track(1, "Song A", "Album A", List.of(1,2,3), LocalDate.of(2018,1,1), 180, com.expeditors.musicaltrack.domain.MediaType.mp3,0),
                new Track(2, "Song B", "Album B", List.of(1,2,3), LocalDate.of(2022,1,1), 60, com.expeditors.musicaltrack.domain.MediaType.ogg ,0),
                new Track(3, "Song C", "Album C", List.of(1,2,3), LocalDate.of(2024,1,1), 120, com.expeditors.musicaltrack.domain.MediaType.wav,0),
                new Track(4, "Song D", "Album D", List.of(1,2,3), LocalDate.of(2021,1,1), 150, com.expeditors.musicaltrack.domain.MediaType.flac,0)
        );

        String jsonString = objectMapper.writeValueAsString(tracks);

        Mockito.when(trackService.getTrackByDuration(durationTrack, seconds)).thenReturn(tracks);

        this.mockMvc.perform(
                        get("/track/duration/" + durationTrack + "/" + seconds)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString))
                .andDo(print());

        Mockito.verify(trackService).getTrackByDuration(durationTrack, seconds);
    }

    @Test
    void getArtistBytrack() throws Exception {
        int idTrack = 1;

        List<Artist> artists = List.of(
                new Artist(12, "Jonh A", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(22, "Jonh O", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(32, "Jonh V", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(42, "Jonh D", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true)
        );

        String jsonString = objectMapper.writeValueAsString(artists);

        Mockito.when(trackService.getArtistsByTrack(idTrack)).thenReturn(artists);

        this.mockMvc.perform(
                        get("/track/artists/" + idTrack)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString))
                .andDo(print());

        Mockito.verify(trackService).getArtistsByTrack(idTrack);
    }

    @Test
    void getSummary() throws Exception {
        List<Track> tracks = List.of(
                new Track(),
                new Track(),
                new Track(),
                new Track()
        );

        Mockito.when(trackService.getAll()).thenReturn(tracks);

        this.mockMvc.perform(
                        get("/track/summary")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tracks").value(tracks.size()))
                .andDo(print());

        Mockito.verify(trackService).getAll();
    }
}