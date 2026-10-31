package com.ionut.tick_sightings_backend;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Service
public class SightingClient {

    private static final int MAX_ATTEMPTS = 3;

    private final RestClient restClient = RestClient.create("https://dev-task.elancoapps.com/data");

    public List<Sighting> fetchAll() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                List<Sighting> result = restClient.get()
                        .uri("/tick-sightings")
                        .retrieve()
                        .body(new ParameterizedTypeReference<List<Sighting>>() {});
                return result == null ? List.of() : result;
            } catch (RestClientResponseException e) {
                int status = e.getStatusCode().value();
                System.out.println("Elanco API returned HTTP " + status
                        + " (attempt " + attempt + " of " + MAX_ATTEMPTS + ")");
                boolean retryable = status == 429 || status >= 500;
                if (!retryable) {
                    break;
                }
            } catch (RestClientException e) {
                System.out.println("Could not reach the Elanco API: " + e.getMessage()
                        + " (attempt " + attempt + " of " + MAX_ATTEMPTS + ")");
            }
            pause(attempt * 3000L);
        }
        System.out.println("WARNING: no data could be loaded from the Elanco API. "
                + "Starting with an empty database.");
        return List.of();
    }

    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}