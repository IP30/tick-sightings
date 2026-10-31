package com.ionut.tick_sightings_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
public class TickSightingsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickSightingsBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(SightingClient client, SightingCleaner cleaner,
						  SightingRepository repository) {
		return args -> {
			List<Sighting> raw = client.fetchAll();
			List<Sighting> cleaned = cleaner.clean(raw);
			List<SightingEntity> entities = cleaned.stream()
					.map(SightingEntity::from)
					.toList();
			repository.saveAll(entities);
			System.out.println("Fetched " + raw.size() + ", kept " + cleaned.size()
					+ ", saved " + repository.count());
		};
	}
}
