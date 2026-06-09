# Club API

A small Spring Boot REST API that lets people register as a club member by filling out a registration form and choosing one of the membership types offered by that form.

## Overview

The API models a simple club-membership sign-up flow. A *form* belongs to a club, has a title, a registration-opens date, and a list of *member types* (e.g. "Social Member", "Active Member"). Users submit a *registration* against a form, picking one of its member types and supplying their personal details. Registrations are persisted to a file-backed H2 database, and a user can only register once per form (enforced by a unique constraint on form + email). On startup a demo form is seeded so the frontend always has a form to fetch instead of getting a 404.

## Features

- **Form seeding on startup** — a demo form is seeded into the database when the app boots (in any profile except `prod`), so the frontend always receives a form instead of a 404. This was done to make sure it is easy to manually test and handle when you are new to the repo. 
- **Add forms via Swagger** — additional forms can be created through the OpenAPI/Swagger UI (the create-form endpoint is intended for demo and testing).
- **Member registration** — once a user fills in all required fields, the registration is validated and saved to the database; duplicate registrations (same email on the same form) are rejected with `409 Conflict`.
- **Validation & problem-detail errors** — request bodies are bean-validated, and failures are returned as RFC 7807 `ProblemDetail` responses.


## Prerequisites

- JDK 25
- Maven 3.9+ (or just use the bundled `./mvnw` wrapper)

## Getting Started

### Installation

```bash
# Clone the repository
git clone https://github.com/username/club.git
cd club

# Build and install dependencies
make build
make run
```

### Configuration

Configuration lives in [application.yaml](src/main/resources/application.yaml). No `.env` file is required.

| Property                        | Description                          | Default                                       |
| ------------------------------- | ------------------------------------ | --------------------------------------------- |
| `app.data.dir`                  | Directory for the H2 database files  | `./data`                                      |
| `spring.datasource.url`         | H2 connection string                 | `jdbc:h2:file:${app.data.dir}/registrations`  |
| `spring.jpa.hibernate.ddl-auto` | Schema management strategy           | `update`                                      |

A `prod` Spring profile is also defined, which disables the Swagger UI / API docs and skips the demo-form seeding.

### Running


```bash
# With Docker
make up
# or
make run
```

The app starts on `http://localhost:8080`.

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **H2 console:** `http://localhost:8080/h2-console`

CORS is configured to allow `GET`/`POST` from the frontend dev origin `http://localhost:5173`.

## Usage

The demo form is seeded with club `britsport` and form id `B171388180BC457D9887AD92B6CCFC86`.

Fetch the seeded form:

```bash
curl http://localhost:8080/clubs/britsport/forms/B171388180BC457D9887AD92B6CCFC86
```

Register a member:

```bash
curl -X POST \
  http://localhost:8080/clubs/britsport/forms/B171388180BC457D9887AD92B6CCFC86/registrations \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Ada",
    "lastName": "Lovelace",
    "email": "ada@example.com",
    "memberTypeId": "4237C55C5CC3B4B082CBF2540612778E",
    "dateOfBirth": "1990-05-12",
    "phoneNumber": "+4712345678"
  }'
```

### Endpoints

| Method | Path                                           | Description                       |
| ------ | ---------------------------------------------- | --------------------------------- |
| `GET`  | `/clubs/{clubId}/forms`               | Get a form and its member types   |
| `POST` | `/clubs/{clubId}/forms`                        | Create a form (demo/testing only) |
| `POST` | `/clubs/{clubId}/forms/{formId}/registrations` | Register a member against a form  |


## Testing

Two simple test have been added to the API to check that the validation works as expected. 

```bash
./mvnw test
# or
make test
```

## Tech Stack and reasoning

- **Language / Runtime:** Java 25. JAva was my preffered choise. It would have been fun to try go - but when the time was limited i choose what i know. 
- **Framework:** Spring Boot 4.0.6 (Spring Web MVC). I chose to use the SpringBoot framework because this is simple and intuitiv. I know it from befor and i have had good experience with using it, especially for bigger apis. As this is small I still chose to use it because the benefits of spring and the structures was still greater than the consequenses with slowwer cold starts . as this is not that relevant here. For further development i also think spring boot implemented from the start makes the expansionn easier. 
- **Database:** H2 (file-backed, persisted under `./data`) File based and serverless for simplicity, bu this is best for development and not production. However, in the future, when we want to change to Postgres the only chnage is in the config - because we are using the JPA and H2 speaks to SQL standards so we do not need a big rewrite. 
- **Persistence:** Spring Data JPA / Hibernate (`ddl-auto: update`). Keeps SQL out of the app, mixing in SQL in java code is nice to avoid when we can. JPA gives a reduces boilerplate and lets you work with the domain objects. ddl-auto update makes it easier to have an overview when changes happen in the models. 
- **API docs:** springdoc-openapi (Swagger UI). API docs is generally a good way to both have controll and manually test the api, and it is a huge benefit for the frontend. Here i wrote the endpoints and generated the documentation from the code - which is a good way to use this as you have full controll. 
- **Validation:** Spring Boot Starter Validation (Jakarta Bean Validation). As with the framework i have har good experience using spring boots validation tools. And since i decided to implement with the framework this was a given. 
- **Build tool:** Maven (via the bundled `./mvnw` wrapper). I simåly chose this because this is what I have used most. Gradle would have been fine too but i chose this mvn here. 

- Docker was added last minute with help from claude code. I wanted to make it smooth and nice to run and spinn up propperly. 


## Future improvements
- Authorization is not included in the spec - it will be better to pretect the endpoint. Depending on how the further development and the separation of clubs and the importanse of identifying users. We could either Authenticate users (Cognito/Spring Security + JWT), which is the propper way, or something simpler to just stopp spem or attackers like rate limit + cors. 
- With expansion of members and clubs it can ie relevant to add a real Database like postgres which is often used with springboot applications. We mifht also need updates, delete as well so with a need for complete CRUD and som relations between the objects thins can be a good choise
- Integration with the admin page. so the forms can be fetched directly. 
- Create more tests. Test the entire service layer. Adding mock services and testing the controllers. Testing all validation. Add tests to the repository layer and test all eroor handling.
- Send email confirmation to the registered user. 

## Disclaimer
Claude code helped me fix the last docker setup and was used to create a template with the techstack added and the structure for the Readme.md files. The reasoning is added by me.  

## Contact

Heidi Brække — heidibraekke@gmail.com
