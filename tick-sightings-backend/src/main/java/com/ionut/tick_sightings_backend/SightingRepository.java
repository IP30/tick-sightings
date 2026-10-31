package com.ionut.tick_sightings_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SightingRepository extends JpaRepository<SightingEntity, String> {

    List<SightingEntity> findBySightingDateBetweenOrderBySightingDate(
            LocalDateTime from, LocalDateTime to);

    List<SightingEntity> findByLocationIgnoreCaseAndSightingDateBetweenOrderBySightingDate(
            String location, LocalDateTime from, LocalDateTime to);

    @Query("""
            select new com.ionut.tick_sightings_backend.LocationCount(s.location, count(s))
            from SightingEntity s
            group by s.location
            order by count(s) desc, s.location
            """)
    List<LocationCount> countByLocation();

    @Query("""
            select new com.ionut.tick_sightings_backend.MonthlyCount(
                year(s.sightingDate), month(s.sightingDate), count(s))
            from SightingEntity s
            group by year(s.sightingDate), month(s.sightingDate)
            order by year(s.sightingDate), month(s.sightingDate)
            """)
    List<MonthlyCount> countByMonth();
}