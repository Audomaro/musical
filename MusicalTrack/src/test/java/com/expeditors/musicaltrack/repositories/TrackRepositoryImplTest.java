package com.expeditors.musicaltrack.repositories;

import com.expeditors.musicaltrack.domain.MediaType;
import com.expeditors.musicaltrack.domain.Track;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrackRepositoryImplTest {

    @Mock
    private TrackRepositoryImpl trackRepository;

    void getAll() {
        List<Track> trackList = List.of(
                new Track(1, "Song A", "Album A", List.of(1,3,3), LocalDate.of(2022,1,1), 120, MediaType.mp3),
                new Track(2, "Song B", "Album B", List.of(1,4,15), LocalDate.of(2018,1,1), 180, MediaType.mp3),
                new Track(3, "Song C", "Album C", List.of(1,62,13), LocalDate.of(2021,1,1), 180, MediaType.mp3),
                new Track(4, "Song D", "Album D", List.of(21,92,1), LocalDate.of(2022,1,1), 60, MediaType.mp3),
                new Track(5, "Song E", "Album E", List.of(1,4,15), LocalDate.of(2019,1,1), 120, MediaType.mp3)
        );

        Mockito.when(trackRepository.getAll()).thenReturn(trackList);

        List<Track> tracks = trackRepository.getAll();

        assertNotNull(tracks);

        assertEquals(trackList.size(), tracks.size());

        Mockito.verify(trackRepository).getAll();
    }

    @Test
    void getById() {
        Track track = new Track(1, List.of(1, 2));

        Mockito.when(trackRepository.getById(track.getId())).thenReturn(track);

        Track result = trackRepository.getById(1);

        assertNotNull(result);

        assertEquals(track.getId(), result.getId());

        Mockito.verify(trackRepository).getById(1);
    }

    @Test
    void insert() {
        Track track = new Track(1, List.of(1, 2));

        Mockito.when(trackRepository.insert(track)).thenReturn(track);

        Track result = trackRepository.insert(track);

        assertNotNull(result);

        assertEquals(track.getId(), result.getId());

        Mockito.verify(trackRepository).insert(track);
    }

    @Test
    void update() {
        Track track = new Track(1, List.of(1, 2));

        Mockito.when(trackRepository.update(track.getId(), track)).thenReturn(true);

        boolean result = trackRepository.update(track.getId(), track);

        assertTrue(result);

        Mockito.verify(trackRepository).update(1, track);
    }

    @Test
    void delete() {
        Track track = new Track(1, List.of(1, 2));

        Mockito.when(trackRepository.delete(track.getId())).thenReturn(true);

        boolean result = trackRepository.delete(track.getId());

        assertTrue(result);

        Mockito.verify(trackRepository).delete(track.getId());
    }

    @Test
    void getBy() {
        MediaType mediaType = MediaType.mp3;

        List<Track> trackList = List.of(
                new Track(1, "Song A", "Album A", List.of(1,3,3), LocalDate.of(2022,1,1), 120, MediaType.mp3),
                new Track(2, "Song B", "Album B", List.of(1,4,15), LocalDate.of(2018,1,1), 180, MediaType.mp3),
                new Track(3, "Song C", "Album C", List.of(1,62,13), LocalDate.of(2021,1,1), 180, MediaType.mp3),
                new Track(4, "Song D", "Album D", List.of(21,92,1), LocalDate.of(2022,1,1), 60, MediaType.mp3),
                new Track(5, "Song E", "Album E", List.of(1,4,15), LocalDate.of(2019,1,1), 120, MediaType.mp3)
        );

        Mockito.when(trackRepository.getBy(Mockito.any())).thenAnswer(invocation -> {
            Predicate<Track> receivedPredicate = invocation.getArgument(0);
            return trackList.stream()
                    .filter(receivedPredicate)
                    .toList();
        });

        Predicate<Track> predicate = t -> t.getMediaType() == mediaType;

        List<Track> result = trackRepository.getBy(predicate);

        assertEquals(trackList, result);

        Mockito.verify(trackRepository).getBy(Mockito.any());
    }
}