package tta.service;

import tta.data.*;

import javax.jws.WebService;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/team/3/flightres/",
        portName          = "ExternalFlightBookingPort",
        serviceName       = "FlightBookingService",
        endpointInterface = "tta.service.FlightReservationService")
public class FlightReservationImpl implements FlightReservationService {

    public FlightReservation simpleReservation(SimpleItineraryRequest request) {
        String originAirport = request.getOriginCountry();
        String destinationAirport = request.getDestinationCountry();
        return book(request.getIdentifier(), originAirport, destinationAirport, request.getDepartureTime());
    }

    private FlightReservation book(String id, String origin, String destination, String date) {
        FlightReservation result = findFlight(origin, destination, date);
        result.setIdentifier(id);
        return result;
    }

    /***************************************
     **        Business Logic Layer       **
     ***************************************/

    private FlightReservation findFlight(String origin, String destination, String date) {
        Flight flight = FlightAgency.getFlight(origin, destination, date);
        if(flight != null) {

            FlightReservation reservation = new FlightReservation();
            float price = 0;
            try {
                price = (float) NumberFormat.getNumberInstance(Locale.FRANCE).parse(
                        flight.getPrice().split("$")[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            reservation.setStartingAirport(origin);
            reservation.setEndingAirport(destination);
            reservation.setDate(date);
            reservation.setPrice(price);
            return reservation;
        }
        return  new FlightReservation();
    }
}