package com.expeditors.musicaltrack.services;

import com.expeditors.musicaltrack.domain.DurationTrack;
import com.expeditors.musicaltrack.domain.MediaType;
import com.expeditors.musicaltrack.domain.Track;

import java.util.List;

public interface TrackService extends ServiceBase<Track> {
    List<Track> getTrackByMediaType(MediaType mediaType);

    List<Track> getTrackByYear(int year);

    List<Track> getTrackByArtist(int idArtist);

    List<Track> getTrackByDuration(DurationTrack durationTrack, int seconds);
}
