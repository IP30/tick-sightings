package com.ionut.tick_sightings_backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SightingRepository extends JpaRepository<SightingEntity, String> {
}