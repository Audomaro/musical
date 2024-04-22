package com.expeditors.musicaltrack.repositories;

import com.expeditors.musicaltrack.domain.Track;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class TrackRepositoryImpl implements TrackRepository {
    private final Map<Integer, Track> listData;

    public TrackRepositoryImpl() {
        this.listData = new ConcurrentHashMap<>();
    }

    @Override
    public List<Track> getAll() {
        return new ArrayList<>(this.listData.values());
    }

    @Override
    public Track getById(int id) {
        return this.listData.get(id);
    }

    @Override
    public Track insert(Track model) {
        this.listData.put(model.id(), model);
        return model;
    }

    @Override
    public boolean update(int id, Track model) {
        return this.listData.replace(id, model) != null;
    }

    @Override
    public boolean delete(int id) {
        return this.listData.remove(id) != null;
    }

    @Override
    public List<Track> getBy(Predicate<Track> predicate) {
        return
                this.listData
                        .values()
                        .stream()
                        .filter(predicate)
                        .collect(Collectors.toList());
    }
}
