# Introduction

I would like to tell you more about the initial setup I had in mind to make this test. Unfortunately, due to the limited time I was not able to make it.

Initial setup:
- 1 Eureka server
- 1 Compare service (micro service)
- 1 Hash service (micro service)
- 1 Api gateway

All those services would live in a mono repository with one gradle file in the root to build all projects and a docker compose to spin up the containers.

As well the hash service as the compare service would have their own database

When the hash is already compared before, it would get the result from the database, else it would retrieve them from the hash service.

All traffic would go through the api gateway and all microservices would have their own unit and integration tests.

On top I would have made an e2e test in Cypress and a deployment script for kubernetes.

## How to run the application

Run with h2 database (dev profile - h2db)
```
./gradlew bootRun
```


Run with docker (prod profile - mysql) 
```
docker-compose up
./gradlew bootRun --args='--spring.profiles.active=prod'

```

Endpoints:
- http://localhost:8080/v1/diff/#ID/left
- http://localhost:8080/v1/diff/#ID/right

Json payload:
```
{
 "hash": "<base64>"
}
```


- http://localhost:8080/v1/diff/#ID

## Suggestions for improvements
- Higher code coverage.
- Better error handling
- Jacoco 
- Lombok
- Sonarqube integration (gradle)
- Swagger / open api
- One wrapper DTO for response and error.

