package com.expeditors.musicaltrack.domain;

import java.util.Random;

public enum MediaType {
    ogg,
    mp3,
    flac,
    wav;

    private static final Random random = new Random();

    public static MediaType getRandomMediaType() {
        MediaType[] values = MediaType.values();
        return values[random.nextInt(values.length)];
    }
}
