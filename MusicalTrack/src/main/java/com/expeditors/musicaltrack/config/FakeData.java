package com.expeditors.musicaltrack.config;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.services.ArtistService;
import com.expeditors.musicaltrack.services.TrackService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class FakeData implements CommandLineRunner {
    final
    ArtistService artistService;
    TrackService trackService;

    public FakeData(ArtistService artistService, TrackService trackService) {
        this.artistService = artistService;
        this.trackService = trackService;
    }

    @Override
    public void run(String... args) {

        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            this.artistService.insert(new Artist());
        }

        List<Artist> artists = this.artistService.getAll();
        int topNumTrack = rand.nextInt(50);

        for (int i = 0; i < topNumTrack; i++) {
            Collections.shuffle(artists);
            Collections.shuffle(artists);
            List<Artist> artistsSelected = artists.subList(0, rand.nextInt(1,3));
            List<Integer> ids = artistsSelected.stream().map(Artist::id).toList();
            this.trackService.insert(new Track(ids));
        }
    }
}