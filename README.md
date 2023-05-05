# ABN AMRO Flight Search Assignment

This project is an assignment for a ABN AMRO Bank. It is a RESTful API project about flight search.

## Project Description
This RESTful API project use cases are;
- List flights from Origin to Destination
- User can add sort parameters in the request to sort results.

The main classes are as follows;
* FlightController: It is the only controller which has the above requirements as an endpoint.
* FlightService: This is an interface for services
* FlightServiceImpl: This is the class inherited the interface.
* FlightLoader: This class inserts init data into flight table if there is no data. The insertion happens during application start by CommandLineRunner interface.

There is only 1 table used for the project.
* Flight: The table stored the flight information.
* FlightPagedList: This class is used for Pagination.

## Technologies Used
To develop this project, below technologies used:
- Java 11
- SpringBoot 2.7.12
- ModelMapper (To map entities to Dto and vice versa)
- Lombok
- Posgresql DB (image version 13)
- Swagger UI
- Docker
- Spring boot Starter test (which includes JUnit5 and Mockito)

## Installation

To install the project;
1. Clone the repository from this url [Flight Application](https://github.com/polatskiDev/flight.git)
2. You need to have docker installed in your local machine. You can install from [this](https://docs.docker.com/get-docker/) url.
3. You also need maven in you local machine. (Check [here](https://maven.apache.org/install.html))
4. Run docker on your local.
5. Locate the project folder where pom.xml and docker-compose-yml exists from terminal.
6. run `./mvnw clean package -DskipTests` to create jar file.
7. When jar is created, run this command `docker-compose up`.
8. When containers are up, go to this url [Flight Search URL](http://localhost:8080/swagger-ui/index.html)

## Usage

There is only one endpoint in the project. 

Search with Origin and Destination: GET /api/v1/flights?origin=BOM&destination=DEL. This endpoint without sort parameter returns the datas of origin and destination values.
Search and Sort: GET /api/v1/flights?origin=BOM&destination=DEL&sort=duration,desc&sort=price,desc. This endpoint returns the result by sorting duration descending and price descending order.

The sort parameter must have with this format {fieldName,sortOrder}
fieldName can be;
* flightNumber
* origin
* destination
* departureTime
* arrivalTime
* price
* duration(this parameter calculates the difference between departureTime and arrivalTime)

sortOrder can be;
* asc
* desc
