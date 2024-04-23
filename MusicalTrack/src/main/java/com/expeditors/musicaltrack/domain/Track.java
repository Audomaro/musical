package com.expeditors.musicaltrack.domain;

import com.github.javafaker.Faker;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                Track.randomAdoptionDate(),
                faker.number().numberBetween(60,600),
                MediaType.getRandomMediaType()
        );
    }

    public Track(int id, List<Integer> idArtist) {
        this(
                id,
                faker.funnyName().name(),
                faker.funnyName().name(),
                new ArrayList<>(),
                Track.randomAdoptionDate(),
                faker.number().numberBetween(60,600),
                MediaType.getRandomMediaType()
        );

        this.idsArtist.addAll(idArtist);
    }

    public Track(List<Integer> idArtist) {
        this(
                faker.number().numberBetween(1, 999),
                faker.funnyName().name(),
                faker.funnyName().name(),
                new ArrayList<>(),
                Track.randomAdoptionDate(),
                faker.number().numberBetween(60,600),
                MediaType.getRandomMediaType()
        );

        this.idsArtist.addAll(idArtist);
    }


    public static  LocalDate randomAdoptionDate() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(2018, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.now().toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}

