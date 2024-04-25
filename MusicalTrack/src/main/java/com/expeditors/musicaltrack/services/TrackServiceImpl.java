package com.expeditors.musicaltrack.services;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.domain.DurationTrack;
import com.expeditors.musicaltrack.domain.MediaType;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.repositories.ArtistRepository;
import com.expeditors.musicaltrack.repositories.TrackRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class TrackServiceImpl implements TrackService{
    private final TrackRepository trackRepository;
    private final ArtistRepository artistRepository;

    public TrackServiceImpl(TrackRepository trackRepository, ArtistRepository artistRepository) {
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Track> getAll() {
        return this.trackRepository.getAll();
    }

    @Override
    public Track getById(int id) {
        return this.trackRepository.getById(id);
    }

    @Override
    public Track insert(Track model) {
        return this.trackRepository.insert(model);
    }

    @Override
    public boolean update(int id, Track model) {
        return this.trackRepository.update(id,model);
    }

    @Override
    public boolean delete(int id) {
        return this.trackRepository.delete(id);
    }

    @Override
    public List<Track> getTrackByMediaType(MediaType mediaType) {
        Predicate<Track> comparator = track -> track.getMediaType() == mediaType;
        return this.trackRepository.getBy(comparator);
    }

    @Override
    public List<Track> getTrackByYear(int year) {
        Predicate<Track> comparator = track -> track.getIssueDate().getYear() == year;
        return this.trackRepository.getBy(comparator);
    }

    @Override
    public List<Artist> getArtistsByTrack(int idTrack) {
        Predicate<Track> predicate = track -> track.getId() == idTrack;

        List<Integer> result = trackRepository
                                .getBy(predicate)
                                .stream()
                                .map(Track::getIdsArtist)
                                .findFirst()
                                .orElse(null);

        if (result == null) {
            result = Collections.emptyList();
        }

        Set<Integer> finalResult = new HashSet<>(result);
        Predicate<Artist> artistPredicate = artist -> finalResult.contains(artist.getId());

        return this.artistRepository.getBy(artistPredicate);
    }

    @Override
    public List<Track> getTrackByDuration(DurationTrack durationTrack, int seconds) {

        Predicate<Track> comparator = switch (durationTrack) {
            case shorted -> (track -> track.getDuration() < seconds);
            case longer -> (track -> track.getDuration() > seconds);
            default -> (track -> track.getDuration() == seconds);
        };

        return this.trackRepository.getBy(comparator);
    }
}
