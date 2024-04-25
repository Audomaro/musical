package com.expeditors.musicaltrack.services;

import com.expeditors.musicaltrack.domain.Artist;
import com.expeditors.musicaltrack.domain.Track;

import java.util.List;

public interface ArtistService extends ServiceBase<Artist> {
    List<Artist> getArtistByName(String name);

    List<Track> getTracksByArtist(int idArtist);
}
