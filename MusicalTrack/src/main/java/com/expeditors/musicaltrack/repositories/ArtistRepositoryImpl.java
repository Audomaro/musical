package com.expeditors.musicaltrack.repositories;

import com.expeditors.musicaltrack.domain.Artist;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class ArtistRepositoryImpl implements ArtistRepository {
    private final Map<Integer, Artist> listData;
    private static int nextId = 1;

    public ArtistRepositoryImpl() {
        this.listData = new ConcurrentHashMap<>();
    }

    @Override
    public List<Artist> getAll() {
        return new ArrayList<>(this.listData.values());
    }

    @Override
    public Artist getById(int id) {
        return this.listData.get(id);
    }

    @Override
    public Artist insert(Artist model) {
        int newId = nextId++;
        model.setId(newId);
        this.listData.put(newId, model);
        return model;
    }

    @Override
    public boolean update(int id, Artist model) {
        return this.listData.replace(id, model) != null;
    }

    @Override
    public boolean delete(int id) {
        return this.listData.remove(id) != null;
    }

    @Override
    public List<Artist> getBy(Predicate<Artist> predicate) {
        return
                this.listData
                        .values()
                        .stream()
                        .filter(predicate)
                        .collect(Collectors.toList());
    }
}