# Flight Reservation Service
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

### Simple reservation request example

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:flig="http://informatique.polytech.unice.fr/soa1/team/3/flightres/">
   <soapenv:Header/>
   <soapenv:Body>
      <flig:simpleReservation>
         <!--Optional:-->
         <simpleItineraryInfo>
            <departureTime>2017-10-25</departureTime>
            <destinationCountry>United Kingdom</destinationCountry>
            <id>ah</id>
            <originCountry>Denmark</originCountry>
         </simpleItineraryInfo>
      </flig:simpleReservation>
   </soapenv:Body>
</soapenv:Envelope>
```
### Simple reservation response example

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:simpleReservationResponse xmlns:ns2="http://informatique.polytech.unice.fr/soa1/team/3/flightres/">
         <simple_booking>
            <date>2017-10-25 22:10:53</date>
            <price>0.0</price>
            <startingAirport>Denmark</startingAirport>
            <endingAirport>United Kingdom</endingAirport>
            <identifier>ah</identifier>
         </simple_booking>
      </ns2:simpleReservationResponse>
   </soap:Body>
</soap:Envelope>
```


### List of possible reservation request example

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:flig="http://informatique.polytech.unice.fr/soa1/team/3/flightres/">
   <soapenv:Header/>
   <soapenv:Body>
      <flig:listPossibleReservation>
         <!--Optional:-->
         <itineraryInfo>
            <departureTime>2017-10-15</departureTime>
            <destinationCountry>Norway</destinationCountry>
            <id>truc</id>
            <originCountry>France</originCountry>
         </itineraryInfo>
      </flig:listPossibleReservation>
   </soapenv:Body>
</soapenv:Envelope>
```

### List of possible reservation response example

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:listPossibleReservationResponse xmlns:ns2="http://informatique.polytech.unice.fr/soa1/team/3/flightres/">
         <booking_info>
            <date>2017-10-27 05:24:00</date>
            <endingAirport>Norway</endingAirport>
            <price>269.41</price>
            <startingAirport>France</startingAirport>
         </booking_info>
         <booking_info>
            <date>2017-10-06 09:46:27</date>
            <endingAirport>Norway</endingAirport>
            <price>360.11</price>
            <startingAirport>France</startingAirport>
         </booking_info>
         ...
         <booking_info>
            <date>2017-10-19 20:13:41</date>
            <endingAirport>Norway</endingAirport>
            <price>304.23</price>
            <startingAirport>France</startingAirport>
         </booking_info>
         <booking_info>
            <date>2017-10-21 18:32:38</date>
            <endingAirport>Norway</endingAirport>
            <price>379.46</price>
            <startingAirport>France</startingAirport>
         </booking_info>
      </ns2:listPossibleReservationResponse>
   </soap:Body>
</soap:Envelope>
```