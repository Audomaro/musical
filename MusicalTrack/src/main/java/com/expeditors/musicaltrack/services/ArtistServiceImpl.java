package com.expeditors.musicaltrack.services;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.repositories.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class ArtistServiceImpl implements ArtistService{
    private final ArtistRepository repository;

    public ArtistServiceImpl(ArtistRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Artist> getAll() {
        return this.repository.getAll();
    }

    @Override
    public Artist getById(int id) {
        return this.repository.getById(id);
    }

    @Override
    public Artist insert(Artist model) {
        return this.repository.insert(model);
    }

    @Override
    public boolean update(int id, Artist model) {
        return this.repository.update(id,model);
    }

    @Override
    public boolean delete(int id) {
        return this.repository.delete(id);
    }

    @Override
    public List<Artist> getArtistByName(String name) {
        Predicate<Artist> comparator = track -> track.fullName().contains(name);
        return this.repository.getBy(comparator);
    }
}
