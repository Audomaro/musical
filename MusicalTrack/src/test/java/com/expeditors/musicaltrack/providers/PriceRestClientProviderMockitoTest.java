package com.expeditors.musicaltrack.providers;

import com.expeditors.musicaltrack.domain.Track;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class PriceRestClientProviderMockitoTest {

    @Mock
    private PriceRestClientProvider provider;

    @Test
    void addPrice() {
        Track track = new Track();

        double price = 1.25;

        doAnswer(invocation -> {
            Track trackArgument = invocation.getArgument(0);
            trackArgument.setPrice(price);
            return null;
        }).when(provider).addPrice(track);

        provider.addPrice(track);

        assertEquals(track.getPrice(), price);

        assertTrue(track.getPrice() > 0);

        Mockito.verify(provider).addPrice(track);
    }
}
