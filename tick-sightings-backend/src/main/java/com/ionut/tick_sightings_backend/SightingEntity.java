package com.ionut.tick_sightings_backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "sightings")
public class SightingEntity {

    @Id
    private String id;

    @Column(name = "sighting_date")
    private LocalDateTime sightingDate;

    private String location;
    private String species;
    private String latinName;

    protected SightingEntity() {
    }

    public SightingEntity(String id, LocalDateTime sightingDate, String location,
                          String species, String latinName) {
        this.id = id;
        this.sightingDate = sightingDate;
        this.location = location;
        this.species = species;
        this.latinName = latinName;
    }

    public static SightingEntity from(Sighting s) {
        return new SightingEntity(
                s.id(),
                LocalDateTime.parse(s.date()),
                s.location(),
                s.species(),
                s.latinName());
    }

    public String getId() { return id; }
    public LocalDateTime getSightingDate() { return sightingDate; }
    public String getLocation() { return location; }
    public String getSpecies() { return species; }
    public String getLatinName() { return latinName; }
}
