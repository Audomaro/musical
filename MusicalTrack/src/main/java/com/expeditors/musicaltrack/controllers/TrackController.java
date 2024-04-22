package com.expeditors.musicaltrack.controllers;

import com.expeditors.musicaltrack.domain.DurationTrack;
import com.expeditors.musicaltrack.domain.MediaType;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.services.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
            return ResponseEntity.status(HttpStatus.OK).body("No track with id: " + id);
        }

        return ResponseEntity.ok(adopter);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Track newModel) {
        Track newAdopter = service.insert(newModel);

        URI newResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAdopter.id())
                .toUri();

        return ResponseEntity.created(newResource).build();
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody Track update) {
        boolean result = service.update(update.id(),update);

        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No track with id: " + update.id());
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
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

    @GetMapping("artist/{idArtist}")
    public List<Track> getByMediaType(@PathVariable int idArtist) {
        return this.service.getTrackByArtist(idArtist);
    }

    @GetMapping("duration/{durationTrack}/{seconds}")
    public List<Track> getByDuration(@PathVariable DurationTrack durationTrack, @PathVariable int seconds) {
        return this.service.getTrackByDuration(durationTrack, seconds);
    }
}
