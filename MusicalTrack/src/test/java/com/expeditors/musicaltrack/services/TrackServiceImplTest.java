package com.expeditors.musicaltrack.services;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.domain.DurationTrack;
import com.expeditors.musicaltrack.domain.MediaType;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.repositories.ArtistRepository;
import com.expeditors.musicaltrack.repositories.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrackServiceImplTest {

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private TrackServiceImpl trackService;

    @Test
    void getAll() {
        List<Track> trackList = List.of(
                new Track(List.of(1, 2)),
                new Track(List.of(1, 2)),
                new Track(List.of(1, 2)),
                new Track(List.of(1, 2))
        );

        Mockito.when(trackRepository.getAll()).thenReturn(trackList);

        List<Track> tracks = trackService.getAll();

        assertNotNull(tracks);

        assertEquals(trackList.size(), tracks.size());

        Mockito.verify(trackRepository).getAll();
    }

    @Test
    void getById() {
        Track track = new Track(List.of(1, 2));
        track.setId(1);

        Mockito.when(trackRepository.getById(track.getId())).thenReturn(track);

        Track track1 = trackService.getById(1);

        assertNotNull(track1);

        assertEquals(track.getId(), track1.getId());

        Mockito.verify(trackRepository).getById(1);
    }

    @Test
    void insert() {
        Track track = new Track(List.of(1, 2));

        Mockito.when(trackRepository.insert(track)).thenReturn(track);

        Track result = trackService.insert(track);

        assertNotNull(result);

        assertEquals(track.getId(), result.getId());

        Mockito.verify(trackRepository).insert(track);
    }

    @Test
    void update() {
        Track track = new Track(List.of(1, 2));
        track.setId(1);

        Mockito.when(trackRepository.update(track.getId(), track)).thenReturn(true);

        boolean result = trackService.update(track.getId(), track);

        assertTrue(result);

        Mockito.verify(trackRepository).update(1, track);
    }

    @Test
    void delete() {
        Track track = new Track(List.of(1, 2));

        Mockito.when(trackRepository.delete(track.getId())).thenReturn(true);

        boolean result = trackService.delete(track.getId());

        assertTrue(result);

        Mockito.verify(trackRepository).delete(track.getId());
    }

    @Test
    void getTrackByMediaType() {
        List<Track> trackList = List.of(
                new Track(1, "A", "X", List.of(1, 2, 3), LocalDate.now(), 300, MediaType.mp3),
                new Track(3, "A", "X", List.of(1, 2, 3), LocalDate.now(), 300, MediaType.mp3),
                new Track(4, "A", "X", List.of(1, 2, 3), LocalDate.now(), 300, MediaType.mp3)
                );

        Mockito.when(trackRepository.getBy(Mockito.any())).thenReturn(trackList);

        List<Track> result = trackService.getTrackByMediaType(MediaType.mp3);

        assertNotNull(result);

        assertEquals(trackList.size(), result.size());

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }

    @Test
    void getTrackByYear() {
        int year = 2022;

        List<Track> trackList = List.of(
                new Track(1, "Song A", "X", List.of(1,2,3), LocalDate.of(2022,1,1), 250, MediaType.mp3),
                new Track(1, "Song B", "X", List.of(1,2,3), LocalDate.of(2022,1,1), 330, MediaType.flac),
                new Track(1, "Song C", "X", List.of(1,2,3), LocalDate.of(2022,1,1), 410, MediaType.mp3),
                new Track(1, "Song D", "X", List.of(1,2,3), LocalDate.of(2022,1,1), 100, MediaType.ogg)
        );

        Mockito.when(trackRepository.getBy(Mockito.any())).thenReturn(trackList);

        List<Track> result = trackService.getTrackByYear(year);

        assertNotNull(result);

        assertEquals(trackList.size(), result.size());

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }

    @Test
    void getTrackByDurationShorted() {
        int secondsDuration = 200;

        List<Track> trackList = List.of(
                new Track(1, "Song A", "Album A", List.of(1,3,3), LocalDate.of(2022,1,1), 120, MediaType.mp3),
                new Track(2, "Song C", "Album C", List.of(1,62,13), LocalDate.of(2021,1,1), 180, MediaType.mp3),
                new Track(3, "Song D", "Album D", List.of(21,92,1), LocalDate.of(2022,1,1), 60, MediaType.ogg)
        );

        Mockito.when(trackRepository.getBy(Mockito.any())).thenAnswer(invocation -> {
            Predicate<Track> receivedPredicate = invocation.getArgument(0);
            return trackList.stream()
                    .filter(receivedPredicate)
                    .toList();
        });

        List<Track> result = trackService.getTrackByDuration(DurationTrack.shorted, secondsDuration);

        assertNotNull(result);

        assertEquals(trackList.size(), result.size());

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }

    @Test
    void getTrackByDurationLonger() {
        int secondsDuration = 200;

        List<Track> trackList = List.of(
                new Track(1, "Song A", "Album A", List.of(1,3,3), LocalDate.of(2022,1,1), 220, MediaType.mp3),
                new Track(2, "Song C", "Album C", List.of(1,62,13), LocalDate.of(2021,1,1), 280, MediaType.mp3),
                new Track(3, "Song D", "Album D", List.of(21,92,1), LocalDate.of(2022,1,1), 260, MediaType.ogg)
        );

        Mockito.when(trackRepository.getBy(Mockito.any())).thenReturn(trackList);

        List<Track> result = trackService.getTrackByDuration(DurationTrack.longer, secondsDuration);

        assertNotNull(result);

        assertEquals(trackList.size(), result.size());

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }


    @Test
    void getTrackByDurationEquals() {
        int secondsDuration = 300;

        List<Track> trackList = List.of(
                new Track(1, "Song A", "Album A", List.of(1,3,3), LocalDate.of(2022,1,1), 300, MediaType.mp3),
                new Track(2, "Song C", "Album C", List.of(1,62,13), LocalDate.of(2021,1,1), 300, MediaType.mp3),
                new Track(3, "Song D", "Album D", List.of(21,92,1), LocalDate.of(2022,1,1), 300, MediaType.ogg)
                );

        Mockito.when(trackRepository.getBy(Mockito.any())).thenReturn(trackList);

        List<Track> result = trackService.getTrackByDuration(DurationTrack.equal, secondsDuration);

        assertNotNull(result);

        assertEquals(trackList.size(), result.size());

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }

    @Test
    void getArtistsByTrack() {
        int idTrack = 1;

        List<Artist> artistList = List.of(
                new Artist(12, "John A", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(22, "John O", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true)
        );

        Mockito.when(artistRepository.getBy(Mockito.any())).thenReturn(artistList);

        List<Artist> result = trackService.getArtistsByTrack(idTrack);

        assertEquals(artistList, result);

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }

    @Test
    void getArtistsByTrackNull() {
        int idTrack = 1;

        Mockito.when(trackRepository.getBy(Mockito.any())).thenReturn(Collections.emptyList());

        List<Artist> result = trackService.getArtistsByTrack(idTrack);

        assertEquals(Collections.emptyList(), result);

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }
}