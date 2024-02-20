# Software Development Practice

# Introduction
This is the backend component of a Hotel Management System developed using the Spring Boot.
It provides a set of RESTful APIs for managing hotel-related functionalities such as room booking, guest management, and reservation tracking.

## Prerequisites
Ensure you have the following installed on your machine:

- [Intellij IDEA](https://www.jetbrains.com/de-de/idea/download/?section=windows) Latest Version 
- [Java Development kit](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)(JDK) 21

## Getting Started
1. Clone the Repository:
```
git clone https://gitlab.com/cris.kohn.2207/software-development-practice.git
```
2. Navigate to the Project Directory and open the project in IntelliJ.

3. Verify the configuration for the database connection settings in the `application.yaml` file located in the `src/main/resources` directory.

4. We are using the Spring Data JPA with JpaRepository. so, the tables in the database will be created automatically once the program is run. There is no need to execute the SQL scripts explicitly.

5. All the store procedures are stored in the `DML` and `DDL` are located in the `SQL-Scripts`.


## Project Structure 
The project follows the standard Spring Boot Structure. Key packages include:
- `com.example.hotelmanagementsystem`: Main Package

- `com.example.hotelmanagementsystem.configs`: CORS configuration are done in this Package.

- `com.example.hotelmanagementsystem.controllers`: This package contains the RESTful endpoints that handle incoming HTTP requests.

- `com.example.hotelmanagementsystem.dto`: Data Transfer Objects are defined in this package

- `com.example.hotelmanagementsystem.models`: Contains Entity classes that represent the structure of data and are mapped to the database tables.

- `com.example.hotelmanagementsystem.repositories`: Interfaces that implement JpaRepository are provided in this package that performs the operations on the tables.  

- `com.example.hotelmanagementsystem.security`: All security-related configurations and functionality are present in this package.

- `com.example.hotelmanagementsystem.services`: It contains the business logic of the application.
 
## Database Information :

The database login details will be shared with you via the email address.