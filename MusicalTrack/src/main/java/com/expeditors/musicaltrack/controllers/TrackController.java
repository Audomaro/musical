package com.expeditors.musicaltrack.controllers;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.domain.DurationTrack;
import com.expeditors.musicaltrack.domain.MediaType;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.services.TrackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Track Controller", description = "Contains methods related to operations on track")
@RestController
@RequestMapping("/track")
public class TrackController {

    private final TrackService service;

    public TrackController(TrackService service) {
        this.service = service;
    }

    @GetMapping
    public List<Track> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Track adopter = service.getById(id);

        if (adopter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No track with id: " + id);
        }

        return ResponseEntity.ok(adopter);
    }

    @PostMapping
    public ResponseEntity<?> postArtist(@RequestBody Track newModel) {
        Track newAdopter = service.insert(newModel);

        URI newResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAdopter.getId())
                .toUri();

        return ResponseEntity.created(newResource).build();
    }

    @PutMapping
    public ResponseEntity<?> updateArtist(@RequestBody Track update) {
        boolean result = service.update(update.getId(),update);

        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No track with id: " + update.getId());
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable int id) {
        boolean result = service.delete(id);

        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No track with id: " + id);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("media-type/{mediaType}")
    public List<Track> getByMediaType(@PathVariable MediaType mediaType) {
        return this.service.getTrackByMediaType(mediaType);
    }

    @GetMapping("year/{year}")
    public List<Track> getByYear(@PathVariable int year) {
        return this.service.getTrackByYear(year);
    }

    @GetMapping("artists/{idArtist}")
    public List<Artist> getByArtist(@PathVariable int idArtist) {
        return this.service.getArtistsByTrack(idArtist);
    }

    @GetMapping("duration/{durationTrack}/{seconds}")
    public List<Track> getByDuration(@PathVariable DurationTrack durationTrack, @PathVariable int seconds) {
        return this.service.getTrackByDuration(durationTrack, seconds);
    }

    @GetMapping("summary")
    public ResponseEntity<?> getSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(new Object(){
            public final int tracks = service.getAll().size();
            public final int mp3 = service.getTrackByMediaType(MediaType.mp3).size();
            public final int ogg = service.getTrackByMediaType(MediaType.ogg).size();
            public final int flac = service.getTrackByMediaType(MediaType.flac).size();
            public final int wav = service.getTrackByMediaType(MediaType.wav).size();
        });
    }
}
