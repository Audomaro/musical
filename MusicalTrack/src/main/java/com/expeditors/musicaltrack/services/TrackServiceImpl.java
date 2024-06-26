package com.expeditors.musicaltrack.services;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.domain.DurationTrack;
import com.expeditors.musicaltrack.domain.MediaType;
import com.expeditors.musicaltrack.domain.Track;
import com.expeditors.musicaltrack.providers.PriceRestClientProvider;
import com.expeditors.musicaltrack.repositories.ArtistRepository;
import com.expeditors.musicaltrack.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TrackServiceImpl implements TrackService{
    private final TrackRepository trackRepository;
    private final ArtistRepository artistRepository;
    private final PriceRestClientProvider priceRestClientProvider;

    public TrackServiceImpl(TrackRepository trackRepository, ArtistRepository artistRepository, PriceRestClientProvider priceRestClientProvider) {
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
        this.priceRestClientProvider = priceRestClientProvider;
    }

    @Override
    public List<Track> getAll() {
        List<Track> result = this.trackRepository.getAll();

        result.forEach(priceRestClientProvider::addPrice);

        return result;
    }

    @Override
    public Track getById(int id) {
        Track result = this.trackRepository.getById(id);
        priceRestClientProvider.addPrice(result);
        return result;
    }

    @Override
    public Track insert(Track model) {
        Track result =  this.trackRepository.insert(model);
        priceRestClientProvider.addPrice(result);
        return result;

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
        Predicate<Track> predicate = track -> track.getMediaType() == mediaType;

        List<Track> result = this.trackRepository.getBy(predicate);

        result.forEach(priceRestClientProvider::addPrice);

        return result;
    }

    @Override
    public List<Track> getTrackByYear(int year) {
        Predicate<Track> predicate = track -> track.getIssueDate().getYear() == year;

        List<Track> result = this.trackRepository.getBy(predicate);

        result.forEach(priceRestClientProvider::addPrice);

        return result;
    }

    @Override
    public List<Artist> getArtistsByTrack(int idTrack) {
        Set<Integer> result = new HashSet<>(
                trackRepository
                        .getBy(track -> track.getId() == idTrack)
                        .stream()
                        .map(Track::getIdsArtist)
                        .findFirst()
                        .orElse(Collections.emptyList())
        );

        return this.artistRepository.getBy(artist -> result.contains(artist.getId()));
    }

    @Override
    public List<Track> getTrackByDuration(DurationTrack durationTrack, int seconds) {

        Predicate<Track> predicate = switch (durationTrack) {
            case shorted -> (track -> track.getDuration() < seconds);
            case longer -> (track -> track.getDuration() > seconds);
            default -> (track -> track.getDuration() == seconds);
        };

        List<Track> result = this.trackRepository.getBy(predicate);

        result.forEach(priceRestClientProvider::addPrice);

        return result;
    }
}
