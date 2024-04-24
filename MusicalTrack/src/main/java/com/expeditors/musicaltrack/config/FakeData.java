//package com.expeditors.musicaltrack.config;
//
//import com.expeditors.musicaltrack.domain.Artist;
//import com.expeditors.musicaltrack.domain.Track;
//import com.expeditors.musicaltrack.services.ArtistService;
//import com.expeditors.musicaltrack.services.TrackService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class FakeData implements CommandLineRunner {
//    final
//    ArtistService artistService;
//    TrackService trackService;
//
//    public FakeData(ArtistService artistService, TrackService trackService) {
//        this.artistService = artistService;
//        this.trackService = trackService;
//    }
//
//    @Override
//    public void run(String... args) {
//        Random rand = new Random();
//
//        int topArtist = rand.nextInt(100,200);
//        int topTrack = rand.nextInt(50,400);
//
//        for (int idAuto = 1; idAuto <= topArtist; idAuto++) {
//            this.artistService.insert(new Artist(idAuto));
//        }
//
//        List<Artist> artists = this.artistService.getAll();
//
//        for (int idAuto = 1; idAuto <= topTrack; idAuto++) {
//            Collections.shuffle(artists);
//            List<Artist> artistsSelected = artists.subList(0, rand.nextInt(1,3));
//            List<Integer> ids = artistsSelected.stream().map(Artist::getId).toList();
//            this.trackService.insert(new Track(idAuto,ids));
//        }
//    }
//}