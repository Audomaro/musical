package com.expeditors.musicaltrack.repositories;


import com.expeditors.musicaltrack.domain.Artist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArtistRepositoryImplTest {
    @Mock
    private ArtistRepositoryImpl artistRepository;

    @Test
    public void getAll() {
        List<Artist> artistList = List.of(
                new Artist(1),
                new Artist(2),
                new Artist(3),
                new Artist(4)
        );

        Mockito.when(artistRepository.getAll()).thenReturn(artistList);

        List<Artist> result = artistRepository.getAll();

        assertEquals(artistList,result);

        Mockito.verify(artistRepository).getAll();
    }

    @Test
    void getById() {
        Artist artist = new Artist(1);

        Mockito.when(artistRepository.getById(1)).thenReturn(artist);

        Artist result = artistRepository.getById(1);

        assertEquals(artist, result);

        Mockito.verify(artistRepository).getById(1);
    }

    @Test
    void insert() {
        Artist newArtist = new Artist(1);

        Mockito.when(artistRepository.insert(newArtist)).thenReturn(newArtist);

        Artist result = artistRepository.insert(newArtist);

        assertEquals(newArtist.getId(), result.getId());

        Mockito.verify(artistRepository).insert(newArtist);
    }

    @Test
    void update() {
        Artist upadateArtist = new Artist(1, "Jonh", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true);

        Mockito.when(artistRepository.update(1, upadateArtist)).thenReturn(true);

        boolean result = artistRepository.update(1, upadateArtist);

        assertTrue(result);

        Mockito.verify(artistRepository).update(1, upadateArtist);
    }

    @Test
    void delete() {
        int deleteArtistId = 1;

        Mockito.when(artistRepository.delete(deleteArtistId)).thenReturn(true);

        boolean result = artistRepository.delete(deleteArtistId);

        assertTrue(result);

        Mockito.verify(artistRepository).delete(deleteArtistId);
    }

    @Test
    void getArtistByName() {
        String artistName = "Jonh";

        List<Artist> artistList = List.of(
                new Artist(12, "Jonh A", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(22, "Jonh O", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(32, "Jonh V", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true),
                new Artist(42, "Jonh D", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true)
        );

        Mockito.when(artistRepository.getBy(Mockito.any())).thenAnswer(invocation -> {
            Predicate<Artist> receivedPredicate = invocation.getArgument(0);
            return artistList.stream()
                    .filter(receivedPredicate)
                    .toList();
        });

        Predicate<Artist> predicate = t -> t.getFullName().contains(artistName);

        List<Artist> result = artistRepository.getBy(predicate);

        assertEquals(artistList, result);

        Mockito.verify(artistRepository).getBy(Mockito.any());
    }
}