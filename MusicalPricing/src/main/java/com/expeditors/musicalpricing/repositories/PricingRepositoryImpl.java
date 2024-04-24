package com.expeditors.musicalpricing.repositories;

import org.springframework.stereotype.Repository;

import java.util.Random;
import java.text.DecimalFormat;

@Repository
public class PricingRepositoryImpl implements PricingRepository {

    public double getPrice(int idTrack) {
        Random random = new Random();

        double minPrice = 1.0;
        double maxPrice = 2.0;
        double price = minPrice + (maxPrice - minPrice) * random.nextDouble();

        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(price));
    }
}
