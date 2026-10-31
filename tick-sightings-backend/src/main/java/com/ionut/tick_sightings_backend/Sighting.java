package com.ionut.tick_sightings_backend;

public record Sighting(String id,
                       String date,
                       String location,
                       String species,
                       String latinName) {
}
