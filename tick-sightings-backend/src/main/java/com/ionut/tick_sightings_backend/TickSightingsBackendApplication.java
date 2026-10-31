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
	CommandLineRunner run(SightingClient client) {
		return args -> {
			List<Sighting> sightings = client.fetchAll();
			System.out.println("Fetched " + sightings.size() + " sightings");
		};
	}

}
