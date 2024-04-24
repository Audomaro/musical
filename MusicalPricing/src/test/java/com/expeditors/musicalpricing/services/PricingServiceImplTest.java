package com.expeditors.musicalpricing.services;


import com.expeditors.musicalpricing.repositories.PricingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PricingServiceImplTest {
    @Mock
    private PricingRepository pricingRepository;

    @InjectMocks
    private PricingServiceImpl pricingService;

    @Test
    void getPrice() {
        int idTrack = 1;
        double price = 2.5;

        Mockito.when(pricingRepository.getPrice(idTrack)).thenReturn(price);

        double result = pricingService.getPrice(idTrack);

        assertEquals(result, price);

        Mockito.verify(pricingRepository).getPrice(idTrack);
    }

}