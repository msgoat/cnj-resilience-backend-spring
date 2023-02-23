# cnj-resilience-backend-spring

Simple cloud native java application based on Spring Boot demonstrating the application of resilience patterns 
with [Resilience4J](https://resilience4j.readme.io/docs/getting-started-3) when calls to downstream services are failing.

## Status

![Build Status](https://codebuild.eu-west-1.amazonaws.com/badges?uuid=eyJlbmNyeXB0ZWREYXRhIjoiQ2lMZzN5RFI3UzY4aENlZERzSnQ5UXdHazRpSkkxTmhFdXdQVHI5VWhydHhaL3d5ZTgyV2ZqWG5oLzJXNXd0SmNBc3NHT0x2SGc0eDVJZGZiL1I1dVR3PSIsIml2UGFyYW1ldGVyU3BlYyI6ImY3ektub2haOGh3NC81SjIiLCJtYXRlcmlhbFNldFNlcmlhbCI6MX0%3D&branch=main)

## Release information

Check [changelog](changelog.md) for latest version and release information.

## Synopsis

The [WelcomeController] (src/main/java/group/msg/at/cloud/cloudtrain/adapter/rest/in/WelcomeController.java) returns
[WelcomeItems](src/main/java/group/msg/at/cloud/cloudtrain/core/entity/WelcomeItems.java)
for a given user.

Each `WelcomeItem` is made of domain objects retrieved via downstream services:

* [cnj-resilience-downstream-a](https://github.com/msgoat/cnj-resilience-downstream-a/blob/main/README.md) returns a list of `RecommendedItem`s representing recommended movies or TV series.
* [cnj-resilience-downstream-b](https://github.com/msgoat/cnj-resilience-downstream-b/blob/main/README.md) returns a list of `WatchedItem`s representing watched movies or TV series.

The applied resilience patterns make sure that this service stays at least partially functional even 
if any the downstream services shows abnormal behaviour.

The application itself offers two REST endpoints:

* GET on `/api/v1/welcome/{userId}` is not resilient and will fail if any of the downstream services breaks.
* GET on `/api/v1/resilient/welcome/{userId}` is resilient and will keep on working even if any of the downstream services breaks.

## HOW-TO Add resilience with Resilience4J

### Step 1: Add dependencies

Add the following dependencies to your POM:

````xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot2</artifactId>
    <version>${resilience4j.version}</version>
</dependency>
````

> __Note:__ Make sure that `spring-boot-starter-aop` is part of your POM; otherwise Resilience4J annotations will not work!
