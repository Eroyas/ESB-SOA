# Car Rental Service
Authors: 
* Khadim Gning [(email)](khadim.gning@etu.unice.fr)
This service is implemented using J2EE. It is structured using Maven. The descriptor is the pom.xml file. The file system hierarchy is the following.

## Project structure

```
  khadim@debian:~/Polytech/SOA/ESB-soa/services/flightres$ tree
  .
  ├── rpc.iml
  ├── pom.xml
  ├── README.md
  └── src
      └── main
          ├── java
          │   ├── flightres
          │   │    ├── data 
          │   │    └── # Remote Object used by the service
          │   │
          │   └── service
          │       ├── FlightReservation.java      ## Request errors
          │       └── FlightReservationImpl.java ## Entry Point
          └── webapp
```

## Building the project
This project can be built by running the following command: `mvn clean package`
And the corresponding docker image can be built from the Dockerfile file.

## Developing the service

### Declaring the interface

The service declares 2 operation in the [FlightReservationService](https://github.com/Eroyas/ESB-SOA/blob/master/services/rpc/src/main/java/flightres/service/FlightReservationService.java) interface, which are associated to a data loader method.

```java
@WebService(name="FlightReservation", targetNamespace = "http://informatique.polytech.unice.fr/soa1/team/3/flightres/")
public interface FlightReservationService {

    @WebResult(name="booking_info")
    List<FlightInformation> listPossibleReservation(@WebParam(name="itineraryInfo") SimpleItineraryRequest request);


    @WebResult(name="simple_booking")
    FlightReservation simpleReservation(@WebParam(name="simpleItineraryInfo") SimpleItineraryRequest request);
}
```

The associated request and response classes are available in the [data](https://github.com/Eroyas/ESB-SOA/blob/master/services/rpc/src/main/java/flightres/data) package.

### Implementing the interface

The interface is implemented in the [FlightReservationImpl](https://github.com/Eroyas/ESB-SOA/blob/master/services/rpc/src/main/java/flightres/service/FlightReservationService.java) class.

## Starting the service

* Compiling: mvn clean package will create the file target/tcs-cars-service.war
* Running: mvn tomee:run will deploy the created war inside a TomEE+ server, available on localhost:8080
* The WSDL interface is available at http://localhost:8080/tta-service-rpc/FlightBookingService?wsdl

### Request example

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soa="http://informatique.polytech.unice.fr/soa1/team/3/flightres/">
    <soapenv:Header/>
        <soapenv:Body>
        <soa:simpleReservation>
        <id>palatine-234</id>
        <departureTime>2017-10-25</departureTime>
        <originCountry>Paris</originCountry>
        <destinationCountry>Nice</destinationCountry>
    </soa:simpleReservation>
    </soapenv:Body>
</soapenv:Envelope>
```