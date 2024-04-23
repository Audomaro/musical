package com.expeditors.musicaltrack.domain;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    private int id;
    private String firstName;
    private String lastName;
    private String genre;
    private String country;
    private String biography;
    private String website;
    private String[] socialMediaLinks;
    private boolean active;
    private static final Faker faker = new Faker();

    public Artist(int id) {
        this(
                id,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.music().genre(),
                faker.address().country(),
                faker.lorem().paragraph(),
                faker.internet().url(),
                new String[]{faker.internet().url(), faker.internet().url()},
                faker.bool().bool()
        );
    }

    public Artist(String firstName, String lastName, String genre, String country, String biography, String website, String[] socialMediaLinks, boolean active) {
        this(0, firstName, lastName, genre, country, biography, website, socialMediaLinks, active);
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}