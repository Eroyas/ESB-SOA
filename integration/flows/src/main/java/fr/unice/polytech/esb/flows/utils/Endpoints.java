package fr.unice.polytech.esb.flows.utils;

/**
 * Created by Eroyas on 14/10/17.
 */
public class Endpoints {

    // File Entry Point
    public static final String CSV_INPUT_TRAVEL_PLAN = "file:/servicemix/camel/input?fileName=travelPlan.csv";

    // Internal message queues
    // public static final String MESSAGE_GENERATION = "activemq:message-generation"; Cf historique de git pour la classe
    public static final String CAR_RESERVATION_Q = "activemq:car-reservation";
    public static final String CAR_EXTERNAL_RESERVATION_Q = "direct:car-external-reservation";
    public static final String CAR_INTERNAL_RESERVATION_Q = "direct:car-internal-reservation";


    public static final String HOTEL_RESERVATION_Q = "activemq:hotel-reservation";
    public static final String BOOKING_Q = "activemq:booking";
    // External partners
    public static final String FLIGHT_RESERVATION_Q = "activemq:flight-reservation";

    public static final String CAR_EXTERNAL_RESERVATION = "http:external-cars-service:8080/cars-service/ExternalCarRentalService";
    public static final String CAR_INTERNAL_RESERVATION = "http:internal-cars-service:8080/tta-car-and-hotel/car-rental/search/Nice";

    public static final String CALL_EXTERNAL_FLIGHT_RESERVATION = "http://external-flights-service:8080/flights-service/flight/";
    public static final String CALL_INTERNAL_FLIGHT_RESERVATION = "http://internal-flights-service:8080/tta-service-rpc/FlightBookingService";
    public static final String HOTEL_SEARCH = "http:localhost:8080/hotels-service/ExternalHotelFinderService";

    // Direct endpoints
    public static final String USE_INTERNAL_FLIGHT_RESERVATION = "direct:internal-flight-service";
    public static final String USE_EXTERNAL_FLIGHT_RESERVATION = "direct:external-flight-service";

    // Dead message channel
    public static final String DEAD_PARTNER =  "activemq:global:dead";

    // Servlet endpoints.
    public static final String SEARCH_FLIGHT = "activemq:bus-cheapest-flight";

    // Internal partners

    public static final String HOTEL_SERVICE = "direct:HotelServiceEndpoint";

    public static final String BOOKING_SERVICE = "direct:BookingService";

}
