package com.expeditors.musicaltrack.services;

import com.expeditors.musicaltrack.domain.DurationTrack;
import com.expeditors.musicaltrack.domain.MediaType;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.repositories.TrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class TrackServiceImpl implements TrackService{
    private final TrackRepository repository;

    public TrackServiceImpl(TrackRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Track> getAll() {
        return this.repository.getAll();
    }

    @Override
    public Track getById(int id) {
        return this.repository.getById(id);
    }

    @Override
    public Track insert(Track model) {
        return this.repository.insert(model);
    }

    @Override
    public boolean update(int id, Track model) {
        return this.repository.update(id,model);
    }

    @Override
    public boolean delete(int id) {
        return this.repository.delete(id);
    }

    @Override
    public List<Track> getTrackByMediaType(MediaType mediaType) {
        Predicate<Track> comparator = track -> track.getMediaType() == mediaType;
        return this.repository.getBy(comparator);
    }

    @Override
    public List<Track> getTrackByYear(int year) {
        Predicate<Track> comparator = track -> track.getIssueDate().getYear() == year;
        return this.repository.getBy(comparator);
    }

    @Override
    public List<Track> getTrackByArtist(int idArtist) {
        Predicate<Track> comparator = track -> track.getIdsArtist()
                                                    .stream()
                                                    .anyMatch(ids -> ids == idArtist);
        return this.repository.getBy(comparator);
    }

    @Override
    public List<Track> getTrackByDuration(DurationTrack durationTrack, int seconds) {

        Predicate<Track> comparator = switch (durationTrack) {
            case shorted -> (track -> track.getDuration() < seconds);
            case longer -> (track -> track.getDuration() > seconds);
            default -> (track -> track.getDuration() == seconds);
        };

        return this.repository.getBy(comparator);
    }
}
