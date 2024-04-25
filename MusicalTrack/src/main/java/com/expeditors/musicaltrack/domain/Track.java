package com.expeditors.musicaltrack.domain;

import com.github.javafaker.Faker;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Track {
    private int id;
    private String title;
    private String album;
    private List<Integer> idsArtist;
    private LocalDate issueDate;
    private int duration;
    private MediaType mediaType;
    private double price;

    private static final Faker faker = new Faker();

    public Track() {
        this(
                0,
                faker.funnyName().name(),
                faker.funnyName().name(),
                new ArrayList<>(),
                randomAdoptionDate(),
                faker.number().numberBetween(60, 600),
                MediaType.getRandomMediaType(),
                0
        );
    }

    public Track( List<Integer> idsArtist) {
        this(
                0,
                faker.funnyName().name(),
                faker.funnyName().name(),
                idsArtist,
                randomAdoptionDate(),
                faker.number().numberBetween(60, 600),
                MediaType.getRandomMediaType(),
                0
        );
    }

    public Track(String title, String album, List<Integer> idsArtist, LocalDate issueDate, int duration, MediaType mediaType) {
        this(0, title, album, idsArtist, issueDate, duration, mediaType, 0);
    }

    public static LocalDate randomAdoptionDate() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(2018, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.now().toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}
