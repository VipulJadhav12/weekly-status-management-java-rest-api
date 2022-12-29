
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

Pull the public MySQL docker image and start a docker container using it

```bash
docker pull mysql
```
```bash
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
```

Check and confirm the MySQL docker container is running

```bash
  docker ps -a
```

Access the MySQL database by using the root user with same MYSQL_ROOT_PASSWORD value

```bash
  mysql -h 127.0.0.1 -u root -pmy-secret-pw
```

Run the above source code through mvn command line

```bash
  mvn spring-boot:run
```

Compile and Package the above source code as a JAR

```bash
  mvn clean package
```
or
```bash
  mvn clean package -Dmaven.test.skip=true
```

Run the above packaged source code through java command line. For that, go to the project's target directory

```bash
  java -jar weekly-status-management-rest-api-0.0.1-SNAPSHOT.jar
```

By default, the API will be available at

```bash
  http://localhost:<PORT_NO>/api/v1/projects
```
and
```bash
  http://localhost:<PORT_NO>/api/v1/weekly_statuses
```

## API Reference

### List of Project level APIs:

#### Get default health check

```http
  GET /api/v1/projects
```
#### Get project by ID

```http
  GET /api/v1/projects/getBy=ID/project/{projectId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `projectId`      | `long` | **Required**. Id of project to fetch, must not be negative. |

#### Get all projects

```http
  GET /api/v1/projects/getAllBy=NONE
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `NA` | `NA` |  |

### List of Weekly-Status level APIs:

#### Get default health check

```http
  GET /api/v1/weekly_statuses
```
#### Get weekly-status by ID

```http
  GET /api/v1/weekly_statuses/getBy=ID/weekly_status/{weeklyStatusId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `weeklyStatusId`  | `long`   | **Required**. Id of weekly-status to fetch, must not be negative. |

#### Get weekly-status by ID and PROJECT_ID

```http
  GET /api/v1/weekly_statuses/getBy=PROJECT_ID/project/{projectId}/weekly_status/{weeklyStatusId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `projectId`  | `long`   | **Required**. Id of project to fetch, must not be negative. |
| `weeklyStatusId`  | `long`   | **Required**. Id of weekly-status to fetch, must not be negative. |

#### Get all weekly-statuses

```http
  GET /api/v1/weekly_statuses/getAllBy=NONE
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `NA`  | `NA`   |  |

#### Get all weekly-statuses with pagination

```http
  GET /api/v1/weekly_statuses/getAllBy=NONE/pagination=TRUE/offset/{offset}/pageSize/{pageSize}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `offset` | `int` | **Required**. Zero-based page index, must not be negative. |
| `pageSize` | `int` | **Required**. Size of the page to be returned, must be greater than 0. |

#### Get all weekly-statuses by PROJECT_ID

```http
  GET /api/v1/weekly_statuses/getAllBy=PROJECT_ID/pagination=FALSE/project/{projectId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `projectId`  | `long`   | **Required**. Id of project for which all weekly-statuses to be fetch, must not be negative. |

#### Get all weekly-statuses by PROJECT_ID with pagination

```http
  GET /api/v1/weekly_statuses/getAllBy=PROJECT_ID/pagination=TRUE/offset/{offset}/pageSize/{pageSize}/project/{projectId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `offset` | `int` | **Required**. Zero-based page index, must not be negative. |
| `pageSize` | `int` | **Required**. Size of the page to be returned, must be greater than 0. |
| `projectId`  | `long`   | **Required**. Id of project for which all weekly-statuses to be fetch, must not be negative. |

#### Add a weekly-status by PROJECT_ID

```http
  POST /api/v1/weekly_statuses/addBy=PROJECT_ID/project/{projectId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `projectId`  | `long`   | **Required**. Id of project for which a weekly-status to be added, must not be negative. |

| Data Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `{ "status": "Sample status details in large-text format.", "highlight": "Sample highlight details in large-text format.", "risk": "Sample risk details in large-text format.", "weeklyEndDate": "yyyy-MM-dd" }` | `JSON` |  |

#### Update weekly-status by PROJECT_ID

```http
  PUT /api/v1/weekly_statuses/updateBy=PROJECT_ID/project/{projectId}/weekly_status/{weeklyStatusId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `projectId`  | `long`   | **Required**. Id of project for which a weekly-status to be updated, must not be negative. |
| `weeklyStatusId`  | `long`   | **Required**. Id of weekly-status to fetch and update, must not be negative. |

| Data Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `{ "status": "Sample status details in large-text format.", "highlight": "Sample highlight details in large-text format.", "risk": "Sample risk details in large-text format.", "weeklyEndDate": "yyyy-MM-dd" }` | `JSON` |  |

#### Delete a weekly-status by ID and PROJECT_ID

```http
  DELETE /api/v1/weekly_statuses/deleteBy=ID/project/{projectId}/weekly_status/{weeklyStatusId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `projectId`  | `long`   | **Required**. Id of project for which a weekly-status to be deleted, must not be negative. |
| `weeklyStatusId`  | `long`   | **Required**. Id of weekly-status to delete, must not be negative. |

#### Delete all weekly-statuses by PROJECT_ID

```http
  DELETE /api/v1/weekly_statuses/deleteAllBy=PROJECT_ID/project/{projectId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `projectId`  | `long`   | **Required**. Id of project for which all weekly_statuses to be deleted, must not be negative. |


#### Delete all weekly-statuses

```http
  DELETE /api/v1/weekly_statuses/deleteAllBy=NONE
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `NA`  | `NA`   |  |


## Running Tests

To run tests, run the following commands

Go to the project directory

```bash
  cd weekly-status-management-java-rest-api
```

Open and edit the src/test/resources/application.properties file

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

Pull the public MySQL docker image and start a docker container using it

```bash
  docker pull mysql
```
```bash
  docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
```

Check and confirm the MySQL docker container is running

```bash
  docker ps -a
```

Access the MySQL database by using the root user with same MYSQL_ROOT_PASSWORD value

```bash
  mysql -h 127.0.0.1 -u root -pmy-secret-pw
```

Run the unit test-cases through mvn command line

```bash
  mvn test
```

## Authors

- [@krashnat922](https://github.com/krashnat922)
- [@sachin396](https://github.com/sachin396)
- [@VipulJadhav12](https://github.com/VipulJadhav12)

