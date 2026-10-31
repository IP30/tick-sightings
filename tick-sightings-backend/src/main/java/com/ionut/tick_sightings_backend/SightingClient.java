package com.ionut.tick_sightings_backend;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class SightingClient {

    private final RestClient restClient = RestClient.create("https://dev-task.elancoapps.com/data");

    public List<Sighting> fetchAll() {
        return restClient.get()
                .uri("/tick-sightings")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Sighting>>() {});
    }
}