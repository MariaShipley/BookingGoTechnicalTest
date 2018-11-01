# BookingGo Technical Test Submission

## Requirements
Java 1.8

Maven 3.6 (may work with earlier versions)

## Setup
Command to build project:
```
'mvn clean package compile assembly:single'
```

## Part 1

### Part 1A: Console application to print the search results for Dave's Taxis

#### Windows:
```
'java -cp target\RidewaysTaxiApp-1.0-SNAPSHOT-jar-with-dependencies.jar com.mariashipley.Part1A [pickup latitude] [pickup longitude] [dropoff latitude] [dropoff longitude] [no. passengers (optional)]'
```
e.g.
```
'java -cp target\RidewaysTaxiApp-1.0-SNAPSHOT-jar-with-dependencies.jar com.mariashipley.Part1A 1.234 5.678 -1000 2.1111'
```
#### Unix:
```
'java -cp target/RidewaysTaxiApp-1.0-SNAPSHOT-jar-with-dependencies.jar com.mariashipley.Part1A [pickup latitude] [pickup longitude] [dropoff latitude] [dropoff longitude] [no. passengers (optional)]'
```
e.g.
```
'java -cp target/RidewaysTaxiApp-1.0-SNAPSHOT-jar-with-dependencies.jar com.mariashipley.Part1A 1.234 5.678 -1000 2.1111'
```

### Part 1B: Console application to print the search results for all taxi APIs

#### Windows:
```
'java -cp target\RidewaysTaxiApp-1.0-SNAPSHOT-jar-with-dependencies.jar com.mariashipley.Part1B [pickup latitude] [pickup longitude] [dropoff latitude] [dropoff longitude] [no. passengers (optional)]'
```
e.g.
```
'java -cp target\RidewaysTaxiApp-1.0-SNAPSHOT-jar-with-dependencies.jar com.mariashipley.Part1B 1.234 5.678 -1000 2.1111 5'
```
#### Unix:
```
'java -cp target/RidewaysTaxiApp-1.0-SNAPSHOT-jar-with-dependencies.jar com.mariashipley.Part1B [pickup latitude] [pickup longitude] [dropoff latitude] [dropoff longitude] [no. passengers (optional)]'
```
e.g.
```
'java -cp target/RidewaysTaxiApp-1.0-SNAPSHOT-jar-with-dependencies.jar com.mariashipley.Part1B 1.234 5.678 -1000 2.1111 5'
```

## Part 2: REST API

Command to start the server:

#### Windows:
```
'java -jar target\RidewaysTaxiApp-1.0-SNAPSHOT.jar'
```
#### Unix:
```
'java -jar target/RidewaysTaxiApp-1.0-SNAPSHOT.jar'
```

#### Query format:

```
'http://localhost:8080/RidewaysTaxiApp?pickup=[pickupLatitude],[pickupLongitude]&dropoff=[dropoffLatitude],[dropoffLongitude]&passengers=[passengers]'
```
where passengers is an optional argument

e.g.
```
'http://localhost:8080/RidewaysTaxiApp?pickup=1.2,3.4&dropoff=5.6,7.8&passengers=6'
```

## Additional Information

Maven was used to compile and build the project, and for assembling one of the jars, so that does not rely on Spring Boot.

Spring Boot framework was used for the REST API, as well as for assembling the Part2 jar.

Gson was used for serialization and deserialization of JSONs.

JUnit was used for the unit tests
