package com.expeditors.musicaltrack.domain;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {
    private int id;
    private String title;
    private String album;
    private List<Integer> idsArtist;
    private LocalDate issueDate;
    private int duration;
    private MediaType mediaType;
    private static final Faker faker = new Faker();

    public Track(int id, List<Integer> idsArtist) {
        this(
                id,
                faker.funnyName().name(),
                faker.funnyName().name(),
                new ArrayList<>(),
                randomAdoptionDate(),
                faker.number().numberBetween(60, 600),
                MediaType.getRandomMediaType()
        );

        this.idsArtist.addAll(idsArtist);
    }

    public static LocalDate randomAdoptionDate() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(2018, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.now().toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}

