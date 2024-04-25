package com.expeditors.musicaltrack.services;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.repositories.ArtistRepository;
import com.expeditors.musicaltrack.repositories.TrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class ArtistServiceImpl implements ArtistService{
    private final ArtistRepository artistRepository;
    private final TrackRepository trackRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository, TrackRepository trackRepository) {
        this.artistRepository = artistRepository;
        this.trackRepository = trackRepository;
    }

    @Override
    public List<Artist> getAll() {
        return this.artistRepository.getAll();
    }

    @Override
    public Artist getById(int id) {
        return this.artistRepository.getById(id);
    }

    @Override
    public Artist insert(Artist model) {
        return this.artistRepository.insert(model);
    }

    @Override
    public boolean update(int id, Artist model) {
        return this.artistRepository.update(id, model);
    }

    @Override
    public boolean delete(int id) {
        return this.artistRepository.delete(id);
    }

    @Override
    public List<Artist> getArtistByName(String name) {
        Predicate<Artist> predicate = track -> track.getFullName().contains(name);
        return this.artistRepository.getBy(predicate);
    }

    @Override
    public List<Track> getTracksByArtist(int idArtist) {
        Predicate<Track> predicate = track -> track.getIdsArtist()
                .stream()
                .anyMatch(ids -> ids == idArtist);
        return this.trackRepository.getBy(predicate);
    }
}
