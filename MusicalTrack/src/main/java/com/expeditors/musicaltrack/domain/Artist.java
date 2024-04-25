package com.expeditors.musicaltrack.domain;

import com.github.javafaker.Faker;
import lombok.*;

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
        this.setId(id);
        this.setFirstName(faker.name().firstName());
        this.setLastName(faker.name().lastName());
        this.setGenre(faker.music().genre());
        this.setCountry(faker.address().country());
        this.setBiography(faker.lorem().paragraph());
        this.setWebsite(faker.internet().url());
        this.setSocialMediaLinks(new String[]{faker.internet().url(), faker.internet().url()});
        this.setActive(faker.bool().bool());
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}