# Tick Sightings Backend

Backend for the Elanco 2026 ITSP task (Backend track).
Built by Ionut Popastica.

Video walkthrough: PASTE YOUR VIDEO LINK HERE

## What it does
The application fetches tick sighting records from Elanco's public API
(`https://dev-task.elancoapps.com/data/tick-sightings`), cleans them, stores them
in a database and exposes its own REST endpoints to search the data and to produce
summary reports.

## Tech stack
- Java 17+ and Spring Boot 4.1 (Spring Web, Spring Data JPA)
- H2 in-memory database
- Maven

## How to run
1. Clone the repository.
2. Open the project folder `tick-sightings-backend` (the one that contains `pom.xml` and `mvnw`).
3. Run `./mvnw spring-boot:run` (or run `TickSightingsBackendApplication` from an IDE).
4. The app starts on `http://localhost:8080`.

On start-up the app fetches the data once, cleans it and saves it. The console prints
a line like `Fetched 1000, kept 1000, saved 1000`.

Note: the database is in memory, so it is rebuilt on every start. Elanco's API has a
rate limit, so avoid restarting the app many times in a row.

## Endpoints

### Search and filter
`GET /sightings`

Optional query parameters (can be combined):
- `location` - city name, case-insensitive (e.g. `Liverpool`)
- `from` - start date, format `yyyy-MM-dd`
- `to` - end date, format `yyyy-MM-dd` (the whole day is included)

Examples:
- `/sightings?location=Liverpool`
- `/sightings?from=2020-01-01&to=2020-12-31`
- `/sightings?location=Liverpool&from=2020-01-01&to=2020-12-31`

Results are sorted by date. Each record has `id`, `sightingDate`, `location`,
`species` and `latinName`.

### Reports
- `GET /reports/by-location` - number of sightings per city, busiest first
- `GET /reports/monthly` - number of sightings per year and month (trend over time)

## Data handling plan
The API returns all records in a single response (about 140 KB for 1000 records), so no
paging is needed. Before saving, each record goes through `SightingCleaner`:
1. Records with a missing or blank field are skipped.
2. Records whose date cannot be parsed are skipped.
3. Duplicate `id` values are removed (the first one is kept).
4. Extra spaces are trimmed from all text fields.

In the data I inspected, all 1000 records were already clean, but the checks protect the
application if the data changes.

Dates are stored as real date-time values so that range filtering is done in the
database. The date column is called `sighting_date` because `date` is a reserved word in
SQL.

## Error handling
- **Elanco API failures:** `SightingClient` retries up to 3 times, with a growing pause,
  when the API returns 429 (rate limit), a 5xx error, or cannot be reached. If it still
  fails, the app prints a warning and starts with an empty database instead of crashing.
- **Invalid user input:** an invalid date (for example `from=banana`) or a `from` date
  later than `to` returns HTTP 400 with a JSON message such as
  `{"error":"Invalid value 'banana' for parameter 'from'. Dates must look like 2020-01-31."}`.

## Design decisions
- Separate classes for the API shape (`Sighting`) and the database shape
  (`SightingEntity`), so a change in the API does not force a change in the database.
- Spring Data derived queries for filtering, and JPQL queries for the grouped reports.
- H2 was chosen because it needs no installation and is enough for 1000 records.

## What I would improve with more time
- Use a persistent database (for example PostgreSQL) instead of H2.
- Add automated tests for the cleaner and the endpoints.
- Add pagination to `/sightings` and a species filter.
- Add the optional AI/ML insights from the brief.

- My video
https://youtu.be/zZ3cuDhEmfY?si=oLa-xU0TbPZJ61QN