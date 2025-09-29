# TESTING2.0

This repository contains solutions for Assignment 2, divided into two main parts:

## Part 1
- **Location:** `A2_20216083_20216086_S1/Part 1/part1/src/`
- **Files:**
	- `IMDB.java`: Main implementation for Part 1.
	- `Main.java`: Entry point for running Part 1.
- **Description:**
	- Java-based solution for the first part of the assignment.

## Part 2
- **Location:** `A2_20216083_20216086_S1/Part 2/Software-Testing-Assi2-Backend/`
- **Description:**
	- Spring Boot backend application for managing TODOs.
- **Key Files:**
	- `SoftwareTestingApplication.java`: Main Spring Boot application.
	- `todo/`: Contains all TODO-related logic (models, controllers, services).
	- `pom.xml`: Maven configuration file.
	- `application.properties`: Application configuration.
- **Testing:**
	- Unit and integration tests are located in `src/test/java/com/fcai/SoftwareTesting/`.

## How to Run

### Part 1
1. Navigate to `A2_20216083_20216086_S1/Part 1/part1/src/`.
2. Compile and run `Main.java` using your preferred Java IDE or command line.

### Part 2 (Spring Boot Backend)
1. Navigate to `A2_20216083_20216086_S1/Part 2/Software-Testing-Assi2-Backend/`.
2. Run the following command in PowerShell:
	 ```powershell
	 ./mvnw spring-boot:run
	 ```
3. The backend will start on the default port (usually 8080).

## Authors
- 20216083
- 20216086

## Assignment
See `A2_20216083_20216086_S1/Assignment_2.pdf` for full assignment details.

## Notes
- Make sure you have Java and Maven installed for running the backend.
- For any issues, refer to the `HELP.md` in the backend folder.