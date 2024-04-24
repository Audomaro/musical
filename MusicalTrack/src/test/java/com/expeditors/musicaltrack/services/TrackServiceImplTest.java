package com.expeditors.musicaltrack.services;

import com.expeditors.musicaltrack.domain.DurationTrack;
import com.expeditors.musicaltrack.domain.MediaType;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.repositories.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrackServiceImplTest {

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private TrackServiceImpl trackService;

    @Test
    void getAll() {
        List<Track> trackList = List.of(
                new Track(1, List.of(1, 2)),
                new Track(2, List.of(1, 2)),
                new Track(3, List.of(1, 2)),
                new Track(4, List.of(1, 2))
        );

        Mockito.when(trackRepository.getAll()).thenReturn(trackList);

        List<Track> tracks = trackService.getAll();

        assertNotNull(tracks);

        assertEquals(trackList.size(), tracks.size());

        Mockito.verify(trackRepository).getAll();
    }

    @Test
    void getById() {
        Track track = new Track(1, List.of(1, 2));

        Mockito.when(trackRepository.getById(track.getId())).thenReturn(track);

        Track track1 = trackService.getById(1);

        assertNotNull(track1);

        assertEquals(track.getId(), track1.getId());

        Mockito.verify(trackRepository).getById(1);
    }

    @Test
    void insert() {
        Track track = new Track(1, List.of(1, 2));

        Mockito.when(trackRepository.insert(track)).thenReturn(track);

        Track result = trackService.insert(track);

        assertNotNull(result);

        assertEquals(track.getId(), result.getId());

        Mockito.verify(trackRepository).insert(track);
    }

    @Test
    void update() {
        Track track = new Track(1, List.of(1, 2));

        Mockito.when(trackRepository.update(track.getId(), track)).thenReturn(true);

        boolean result = trackService.update(track.getId(), track);

        assertTrue(result);

        Mockito.verify(trackRepository).update(1, track);
    }

    @Test
    void delete() {
        Track track = new Track(1, List.of(1, 2));

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

        Mockito.when(trackRepository.getBy(Mockito.any())).thenAnswer(invocation -> {
            Predicate<Track> receivedPredicate = invocation.getArgument(0);
            return trackList.stream()
                    .filter(receivedPredicate)
                    .toList();
        });

        List<Track> result = trackService.getTrackByYear(year);

        assertNotNull(result);

        assertEquals(trackList.size(), result.size());

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }

    @Test
    void getTrackByArtist() {
        int idArtist = 1;

        List<Track> trackList = List.of(
                new Track(1, "Song A", "Album A", List.of(1,3,3), LocalDate.of(2022,1,1), 250, MediaType.mp3),
                new Track(2, "Song B", "Album B", List.of(1,4,15), LocalDate.of(2018,1,1), 330, MediaType.flac),
                new Track(3, "Song C", "Album C", List.of(1,62,13), LocalDate.of(2021,1,1), 410, MediaType.mp3),
                new Track(4, "Song D", "Album D", List.of(21,92,1), LocalDate.of(2022,1,1), 100, MediaType.ogg),
                new Track(5, "Song E", "Album E", List.of(1,4,15), LocalDate.of(2019,1,1), 330, MediaType.flac)
        );

        Mockito.when(trackRepository.getBy(Mockito.any())).thenAnswer(invocation -> {
            Predicate<Track> receivedPredicate = invocation.getArgument(0);
            return trackList.stream()
                    .filter(receivedPredicate)
                    .toList();
        });

        List<Track> result = trackService.getTrackByArtist(idArtist);

        assertNotNull(result);

        assertEquals(trackList.size(), result.size());

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }

    @Test
    void getTrackByDuration() {
        DurationTrack durationTrack = DurationTrack.shorted;
        int secondsDuration = 200;

        List<Track> trackList = List.of(
                new Track(1, "Song A", "Album A", List.of(1,3,3), LocalDate.of(2022,1,1), 120, MediaType.mp3),
                new Track(2, "Song B", "Album B", List.of(1,4,15), LocalDate.of(2018,1,1), 180, MediaType.flac),
                new Track(3, "Song C", "Album C", List.of(1,62,13), LocalDate.of(2021,1,1), 180, MediaType.mp3),
                new Track(4, "Song D", "Album D", List.of(21,92,1), LocalDate.of(2022,1,1), 60, MediaType.ogg),
                new Track(5, "Song E", "Album E", List.of(1,4,15), LocalDate.of(2019,1,1), 120, MediaType.flac)
        );

        Mockito.when(trackRepository.getBy(Mockito.any())).thenAnswer(invocation -> {
            Predicate<Track> receivedPredicate = invocation.getArgument(0);
            return trackList.stream()
                    .filter(receivedPredicate)
                    .toList();
        });

        List<Track> result = trackService.getTrackByDuration(durationTrack, secondsDuration);

        assertNotNull(result);

        assertEquals(trackList.size(), result.size());

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }
}