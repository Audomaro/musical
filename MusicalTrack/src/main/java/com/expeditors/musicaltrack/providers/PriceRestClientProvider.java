package com.expeditors.musicaltrack.providers;

import com.expeditors.musicaltrack.domain.Track;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class PriceRestClientProvider implements PriceProvider {

    private final String rootUrl;
    private final RestClient restClient;

    public PriceRestClientProvider() {
        String baseUrl = "http://localhost:8181/pricing";
        rootUrl = baseUrl + "/{idTrack}";

        this.restClient = RestClient.builder()
                .baseUrl(rootUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Override
    public void addPrice(Track track) {
        ResponseEntity<Double> response = restClient.get()
                .uri(this.rootUrl, track.getId())
                .retrieve()
                .toEntity(Double.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Double price = response.getBody();
            if (price != null) {
                track.setPrice(price);
            }
        }
    }
}