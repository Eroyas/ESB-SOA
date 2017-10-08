package flightres.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import flightres.data.*;

@WebService(name="FlightReservation", targetNamespace = "http://informatique.polytech.unice.fr/soa1/team/3/flightres/")
public interface FlightReservationService {

    /*@WebResult(name="select_origin")
    Aeroport selectOrigin(@WebParam(name="startingAirport") AirportRequest request);

    @WebResult(name="select_destination")
    Aeroport selectDestination(@WebParam(name="destinationAirport") AirportRequest request);

    @WebResult(name="selectDate")
    String selectDate(@WebParam(name="flightInfo") ItineraryRequest request);
    */

    @WebResult(name="simple_booking")
    FlightReservation simpleReservation(@WebParam(name="simpleItineraryInfo") SimpleItineraryRequest request);
}