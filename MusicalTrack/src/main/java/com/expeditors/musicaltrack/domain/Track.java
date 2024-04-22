package com.expeditors.musicaltrack.domain;

import com.github.javafaker.Faker;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record Track(
        int id,
        String title,
        String album,
        List<Integer> idsArtist,
        LocalDate issueDate,
        int duration,
        MediaType mediaType
) {

    private static final Faker faker = new Faker();

    public Track() {
        this(
                faker.number().numberBetween(1, 999),
                faker.funnyName().name(),
                faker.funnyName().name(),
                new ArrayList<>(),
                LocalDate.now(),
                faker.number().numberBetween(60,600),
                MediaType.getRandomMediaType()
        );
    }

    public Track(List<Integer> idArtist) {
        this(
                faker.number().numberBetween(1, 999),
                faker.funnyName().name(),
                faker.funnyName().name(),
                new ArrayList<>(),
                LocalDate.now(),
                faker.number().numberBetween(60,600),
                MediaType.getRandomMediaType()
        );

        this.idsArtist.addAll(idArtist);
    }
}

