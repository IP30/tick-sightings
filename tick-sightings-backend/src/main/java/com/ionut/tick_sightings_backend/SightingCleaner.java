package com.ionut.tick_sightings_backend;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SightingCleaner {

    public List<Sighting> clean(List<Sighting> raw) {
        List<Sighting> cleaned = new ArrayList<>();
        if (raw == null) {
            return cleaned;
        }

        Set<String> seenIds = new HashSet<>();

        for (Sighting s : raw) {

            if (isBlank(s.id()) || isBlank(s.date()) || isBlank(s.location())
                    || isBlank(s.species()) || isBlank(s.latinName())) {
                continue;
            }


            try {
                LocalDateTime.parse(s.date().trim());
            } catch (DateTimeParseException e) {
                continue;
            }


            if (!seenIds.add(s.id().trim())) {
                continue;
            }


            cleaned.add(new Sighting(
                    s.id().trim(),
                    s.date().trim(),
                    s.location().trim(),
                    s.species().trim(),
                    s.latinName().trim()));
        }
        return cleaned;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
