package com.expeditors.musicaltrack.domain;

import com.github.javafaker.Faker;

public record Artist(
        int id,
        String firstName,
        String lastName,
        String genre,
        String country,
        String biography,
        String website,
        String[] socialMediaLinks,
        boolean active)
{

    private static final Faker faker = new Faker();

    public Artist() {
        this(
                faker.number().numberBetween(1, 999),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.music().genre(),
                faker.address().country(),
                faker.lorem().paragraph(),
                faker.internet().url(),
                new String[] {faker.internet().url(), faker.internet().url()},
                faker.bool().bool()
        );
    }

    public Artist(int id) {
        this(
                id,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.music().genre(),
                faker.address().country(),
                faker.lorem().paragraph(),
                faker.internet().url(),
                new String[] {faker.internet().url(), faker.internet().url()},
                faker.bool().bool()
        );
    }

    public String fullName() {
        return this.firstName() + " " + this.lastName();
    }
}
