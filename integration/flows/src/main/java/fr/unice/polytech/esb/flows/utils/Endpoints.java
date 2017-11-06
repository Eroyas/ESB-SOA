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
    public static final String CAR_EXTERNAL_RESERVATION_Q = "activemq:car-external-reservation";
    public static final String CAR_INTERNAL_RESERVATION_Q = "activemq:car-internal-reservation";
    public static final String FLIGHT_RESERVATION_Q = "activemq:flight-reservation";
    public static final String HOTEL_RESERVATION_Q = "activemq:hotel-reservation";

    // External partners
    public static final String CAR_RESERVATION = "http:localhost:8080/tcs-cars-service/ExternalCarRentalService";
    public static final String FLIGHT_RESERVATION = "http:localhost:8080/tcs-service-document/registry";

    public static final String HOTEL_SEARCH = "http:localhost:8080/hotels-service/ExternalHotelFinderService";

    // Internal partners
    public static final String CAR_RENTAL = "http:localhost:8080/tta-car-and-hotel/car-rental";

    public static final String HOTEL_SERVICE = "direct:HotelServiceEndpoint";
}
