package com.expeditors.musicaltrack.domain;

import com.github.javafaker.Faker;
import lombok.*;

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
    private double price;

    private static final Faker faker = new Faker();

    public Track( List<Integer> idsArtist) {
        this.setTitle(faker.funnyName().name());
        this.setAlbum(faker.funnyName().name());
        this.setIdsArtist(idsArtist);
        this.setIssueDate(randomAdoptionDate());
        this.setDuration(faker.number().numberBetween(60, 600));
        this.setMediaType(MediaType.getRandomMediaType());
        this.setPrice(faker.number().randomDouble(2, 1,2));
    }

    public static LocalDate randomAdoptionDate() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(2018, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.now().toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}
