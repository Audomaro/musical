package com.expeditors.musicalpricing.repositories;

import com.expeditors.musicalpricing.services.PricingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PricingRepositoryImplTest {

    @Mock
    private PricingRepositoryImpl pricingRepositoryImpl;

    @Test
    void getPrice() {
        double price = 1.69;
        int idTrack =  1;
        Mockito.when(pricingRepositoryImpl.getPrice(idTrack)).thenReturn(price);

        double result = pricingRepositoryImpl.getPrice(idTrack);

        assertEquals(price, result);

        Mockito.verify(pricingRepositoryImpl).getPrice(idTrack);
    }
}