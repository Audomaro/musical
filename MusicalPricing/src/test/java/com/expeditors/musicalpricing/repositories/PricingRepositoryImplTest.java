package com.expeditors.musicalpricing.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PricingRepositoryImplTest {

    @Autowired
    private PricingRepositoryImpl pricingRepository;

    @Test
    void getPrice() {
        double result = pricingRepository.getPrice(1);
        assertTrue(result > 0);
    }
}