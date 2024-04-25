package com.expeditors.musicaltrack.repositories;

import com.expeditors.musicaltrack.domain.Artist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ArtistRepositoryImplTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    void getAll() {
        Artist artist1 = new Artist();
        Artist artist2 = new Artist();

        artistRepository.insert(artist1);
        artistRepository.insert(artist2);

        List<Artist> allArtists = artistRepository.getAll();
        assertEquals(2, allArtists.size());
        assertTrue(allArtists.contains(artist1));
        assertTrue(allArtists.contains(artist2));
    }

    @Test
    void getById() {
        assertNull(artistRepository.getById(1));

        Artist artist = new Artist();

        artistRepository.insert(artist);

        assertEquals(artist, artistRepository.getById(artist.getId()));
    }

    @Test
    void insert() {
        Artist artist = new Artist();

        artistRepository.insert(artist);

        assertEquals(artist, artistRepository.getById(artist.getId()));
    }

    @Test
    void update() {
        String artistName = "Joe";
        Artist artist = new Artist();

        artistRepository.insert(artist);

        artist.setFirstName(artistName);

        assertTrue(artistRepository.update(artist.getId(), artist));

        assertEquals(artistName, artistRepository.getById(artist.getId()).getFirstName());
    }

    @Test
    void delete() {
        Artist artist = new Artist();

        artistRepository.insert(artist);

        assertTrue(artistRepository.delete(artist.getId()));

        assertNull(artistRepository.getById(artist.getId()));
    }

    @Test
    void getBy() {
        Artist artist1 = new Artist();
        artist1.setFirstName("Joe");

        Artist artist2 = new Artist();
        artist2.setFirstName("Joe");

        Artist artist3 = new Artist();
        artist3.setFirstName("Joe");

        artistRepository.insert(artist1);
        artistRepository.insert(artist2);
        artistRepository.insert(artist3);

        List<Artist> filteredArtists = artistRepository.getBy(artist -> artist.getFullName().contains("Joe"));
        assertEquals(3, filteredArtists.size());
    }
}