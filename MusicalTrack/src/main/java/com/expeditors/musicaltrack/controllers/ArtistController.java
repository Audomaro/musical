package com.expeditors.musicaltrack.controllers;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.services.ArtistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService service;

    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping
    public List<Artist> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Artist artist = service.getById(id);

        if (artist == null) {
            return ResponseEntity.status(HttpStatus.OK).body("No artist with id: " + id);
        }

        return ResponseEntity.ok(artist);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        List<Artist> artists = service.getArtistByName(name);

        if (artists == null) {
            return ResponseEntity.status(HttpStatus.OK).body("No artist with name: " + name);
        }

        return ResponseEntity.ok(artists);
    }

    @PostMapping
    public ResponseEntity<?> postArtist(@RequestBody Artist newModel) {
        Artist newArtist = service.insert(newModel);

        URI newResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newArtist.getId())
                .toUri();

        return ResponseEntity.created(newResource).build();
    }

    @PutMapping
    public ResponseEntity<?> putArtist(@RequestBody Artist update) {
        boolean result = service.update(update.getId(),update);

        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No artist with id: " + update.getId());
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable int id) {
        boolean result = service.delete(id);

        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No artist with id: " + id);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("summary")
    public ResponseEntity<?> getSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(new Object(){
            public final int artists = service.getAll().size();
        });
    }
}
