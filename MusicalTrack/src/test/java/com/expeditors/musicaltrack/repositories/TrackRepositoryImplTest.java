package com.expeditors.musicaltrack.repositories;

import com.expeditors.musicaltrack.domain.Track;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TrackRepositoryImplTest {

    @Autowired
    private TrackRepository trackRepository;

    @Test
    void getAll() {
        Track track1 = new Track();
        Track track2 = new Track();

        trackRepository.insert(track1);
        trackRepository.insert(track2);

        List<Track> allTracks = trackRepository.getAll();
        assertEquals(2, allTracks.size());
        assertTrue(allTracks.contains(track1));
        assertTrue(allTracks.contains(track2));
    }

    @Test
    void getById() {
        assertNull(trackRepository.getById(1));

        Track track = new Track();

        trackRepository.insert(track);

        assertEquals(track, trackRepository.getById(track.getId()));
    }

    @Test
    void insert() {
        Track track = new Track();

        trackRepository.insert(track);

        assertEquals(track, trackRepository.getById(track.getId()));
    }

    @Test
    void update() {
        List<Integer> ids = List.of(6,7,8);
        Track track = new Track();

        trackRepository.insert(track);

        track.setIdsArtist(ids);

        assertTrue(trackRepository.update(track.getId(), track));

        assertEquals(ids, trackRepository.getById(track.getId()).getIdsArtist());
    }

    @Test
    void delete() {
        Track track = new Track();

        trackRepository.insert(track);

        assertTrue(trackRepository.delete(track.getId()));

        assertNull(trackRepository.getById(track.getId()));
    }

    @Test
    void getBy() {
        int idArtist = 1;
        Track track1 = new Track();
        track1.setIdsArtist(List.of(idArtist,2,3));
        Track track2 = new Track();
        track2.setIdsArtist(List.of(idArtist,4,53));
        Track track3 = new Track();
        track3.setIdsArtist(List.of(idArtist,62,23));

        trackRepository.insert(track1);
        trackRepository.insert(track2);
        trackRepository.insert(track3);

        List<Track> filteredTracks = trackRepository.getBy(track -> track.getIdsArtist().contains(idArtist));
        assertEquals(3, filteredTracks.size());
    }
}