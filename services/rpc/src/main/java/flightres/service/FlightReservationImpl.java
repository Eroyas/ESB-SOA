package flightres.service;

import flightres.data.*;

import javax.jws.WebService;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/team/3/flightres/",
        portName          = "ExternalFlightBookingPort",
        serviceName       = "FlightBookingService",
        endpointInterface = "flightres.service.FlightReservationService")
public class FlightReservationImpl implements FlightReservationService {

    public List<FlightInformation> listPossibleReservation(SimpleItineraryRequest request)
    {
        String origin = request.getOriginCountry();
        String destination = request.getDestinationCountry();
        List<FlightInformation> listOfFlights = lookupFlights(origin, destination);
        return listOfFlights;
    }

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

    private List<FlightInformation> lookupFlights(String origin, String destination){
        List<Flight> flights = FlightAgency.getAllFlightsBetween(origin, destination);
        List<FlightInformation> flightsInfos = new ArrayList<>();
        for(Flight flight: flights){
            FlightInformation information = new FlightInformation();
            float price = 0;
            try {
                price = NumberFormat.getNumberInstance(Locale.FRANCE).parse(
                        flight.getPrice().split("€")[1]).floatValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            information.setStartingAirport(origin);
            information.setEndingAirport(destination);
            information.setDate(flight.getStartDate());
            information.setPrice(price);
            flightsInfos.add(information);
        }

        return flightsInfos;
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
                price = NumberFormat.getNumberInstance(Locale.FRANCE).parse(
                        flight.getPrice().split("€")[1]).floatValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            reservation.setStartingAirport(origin);
            reservation.setEndingAirport(destination);
            reservation.setDate(flight.getStartDate());
            reservation.setPrice(price);
            return reservation;
        }
        return  new FlightReservation();
    }
}