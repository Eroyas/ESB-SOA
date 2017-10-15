package flightres.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import flightres.data.*;

import java.util.List;

@WebService(name="FlightReservation", targetNamespace = "http://informatique.polytech.unice.fr/soa1/team/3/flightres/")
public interface FlightReservationService {

    @WebResult(name="booking_info")
    List<FlightInformation> listPossibleReservation(@WebParam(name="itineraryInfo") SimpleItineraryRequest request);


    @WebResult(name="simple_booking")
    FlightReservation simpleReservation(@WebParam(name="simpleItineraryInfo") SimpleItineraryRequest request);
}