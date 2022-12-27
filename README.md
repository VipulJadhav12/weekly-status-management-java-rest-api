
# weekly-status-management-java-rest-api

Based on a Microservice Architecture, this project has been designed and developed as a solution for "Problem Statement #2: Weekly Status Mgmt Micro App" of "Weekly Status Reporting Project". This is a simple REST API based application developed with JAVA, Spring Boot, and Spring Framework to manage Weekly Status related CRUD operations.


## Features

This API provides HTTP endpoints for the following operations:

- Create and manage Project wise Weekly-Statuses at company/organization level.
- Find individual Project by id and its related details.
- Find individual Weekly-Status by id, project_id, and weekly_end_date.
- Find Project wise Weekly-Statuses in a sorted and paginated form.

## Requirements

- Java 1.8+
- Maven 3.0+
- Docker Engine
- Latest MySQL

## Tech Stack

- Java 1.8+
- Maven 3.0+
- Spring Boot 2.7.0+
- JUnit 5
- Docker Engine
- Latest MySQL 8.0+

## Run Locally

Clone the project

```bash
  git clone https://github.com/VipulJadhav12/weekly-status-management-java-rest-api.git
```

Go to the project directory

```bash
  cd weekly-status-management-java-rest-api
```
Open and edit the src/main/resources/application.properties file

```bash
  server.port=<PORT_NO>
  server.error.include-message=always

  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.datasource.url=jdbc:mysql://localhost:3306/<DATABASE_NAME>?autoReconnect=true&useSSL=false&createDatabaseIfNotExist=true
  spring.datasource.username=<USERNAME>
  spring.datasource.password=<PASSWORD>
  spring.jpa.generate-ddl=true
  spring.jpa.show-sql=true
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

Run the above source code through mvn command line

```bash
  mvn spring-boot:run
```

Compile and Package the above source code as a JAR

```bash
  mvn clean package

  or

  mvn clean package -Dmaven.test.skip=true
```

Run the above packaged source code through java command line. For that, go to the project's target directory

```bash
  java -jar weekly-status-management-rest-api-0.0.1-SNAPSHOT.jar
```

By default, the API will be available at

```bash
  http://localhost:<PORT_NO>/api/v1/projects

  and

  http://localhost:<PORT_NO>/api/v1/weekly_statuses
```

