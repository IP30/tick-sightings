package com.ionut.tick_sightings_backend;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
public class SightingController {

    private final SightingRepository repository;

    public SightingController(SightingRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/sightings")
    public List<SightingEntity> search(
            @RequestParam(required = false) String location,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        LocalDateTime start = (from != null)
                ? from.atStartOfDay()
                : LocalDateTime.of(1900, 1, 1, 0, 0);
        LocalDateTime end = (to != null)
                ? to.atTime(LocalTime.MAX)
                : LocalDateTime.of(2100, 1, 1, 0, 0);

        if (location == null || location.isBlank()) {
            return repository.findBySightingDateBetweenOrderBySightingDate(start, end);
        }
        return repository
                .findByLocationIgnoreCaseAndSightingDateBetweenOrderBySightingDate(
                        location.trim(), start, end);
    }
}