package com.expeditors.musicalpricing.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PricingRepositoryImplTest {

    @Mock
    private PricingRepositoryImpl pricingRepository;

    @Test
    void getPrice() {
        double price = 1.75;

        Mockito.when(pricingRepository.getPrice(1)).thenReturn(price);

        double result = pricingRepository.getPrice(1);
        assertEquals(price, result);

        Mockito.verify(pricingRepository).getPrice(1);
    }
}