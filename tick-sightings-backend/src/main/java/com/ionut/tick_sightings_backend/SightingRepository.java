package com.ionut.tick_sightings_backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SightingRepository extends JpaRepository<SightingEntity, String> {

    List<SightingEntity> findBySightingDateBetweenOrderBySightingDate(
            LocalDateTime from, LocalDateTime to);

    List<SightingEntity> findByLocationIgnoreCaseAndSightingDateBetweenOrderBySightingDate(
            String location, LocalDateTime from, LocalDateTime to);
}