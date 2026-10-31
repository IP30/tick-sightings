package com.ionut.tick_sightings_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final SightingRepository repository;

    public ReportController(SightingRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/by-location")
    public List<LocationCount> byLocation() {
        return repository.countByLocation();
    }

    @GetMapping("/monthly")
    public List<MonthlyCount> monthly() {
        return repository.countByMonth();
    }
}