package com.expeditors.musicaltrack.services;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.repositories.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceImplTest {
    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistServiceImpl artistService;

    @Test
    void getAll() {
        List<Artist> artistList = List.of(
                new Artist(1),
                new Artist(2),
                new Artist(3),
                new Artist(4)
        );

        Mockito.when(artistRepository.getAll()).thenReturn(artistList);

        List<Artist> result = artistService.getAll();

        assertEquals(artistList, result);

        Mockito.verify(artistRepository).getAll();
    }

    @Test
    void getById() {
        Artist artist1 = new Artist(1);
        Artist artist2 = new Artist(1);

        Mockito.when(artistRepository.getById(1)).thenReturn(artist1);
        Mockito.when(artistRepository.getById(2)).thenReturn(artist2);

        Artist result1 = artistService.getById(1);
        Artist result2 = artistService.getById(2);

        assertEquals(artist1, result1);
        assertEquals(artist2, result2);

        Mockito.verify(artistRepository).getById(1);
        Mockito.verify(artistRepository).getById(2);
    }

    @Test
    void insert() {
        Artist newArtist = new Artist(1);

        Mockito.when(artistRepository.insert(newArtist)).thenReturn(newArtist);

        Artist result = artistService.insert(newArtist);

        assertEquals(newArtist.getId(), result.getId());

        Mockito.verify(artistRepository).insert(newArtist);
    }

    @Test
    void update() {
        Artist upadateArtist = new Artist(1, "Jonh", "Doe", "Modify", "MEx", "N/A", "", new String[]{}, true);

        Mockito.when(artistRepository.update(1, upadateArtist)).thenReturn(true);

        boolean result = artistService.update(1, upadateArtist);

        assertTrue(result);

        Mockito.verify(artistRepository).update(1, upadateArtist);
    }

    @Test
    void delete() {
        int deleteArtistId = 1;

        Mockito.when(artistRepository.delete(deleteArtistId)).thenReturn(true);

        boolean result = artistService.delete(deleteArtistId);

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

        List<Artist> result = artistService.getArtistByName(artistName);

        assertEquals(artistList, result);

        Mockito.verify(artistRepository).getBy(Mockito.any());
    }
}