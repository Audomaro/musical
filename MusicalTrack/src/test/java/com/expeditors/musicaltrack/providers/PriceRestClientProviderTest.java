package com.expeditors.musicaltrack.providers;

import com.expeditors.musicaltrack.domain.Track;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PriceRestClientProviderTest {

    @Autowired
    private PriceRestClientProvider provider;

    @Test
    void addPrice() {
        Track track = new Track();

        provider.addPrice(track);

        assertTrue(track.getPrice() > 0);
    }
}