package fr.unice.polytech.esb.flows.utils;

/**
 * Created by Eroyas on 14/10/17.
 */
public class Endpoints {

    // Trash Output
    public static final String CONSOLE_OUTPUT = "stream:out";
    public static final String CONSOLE_IN_ASK_DESTINATION = "stream:in?promptMessage=Enter destination: ";

    // Internal message queues
    public static final String CAR_RESERVATION_Q = "activemq:car-reservation";
    public static final String FLIGHT_RESERVATION_Q = "activemq:flight-reservation";

    // External partners
    public static final String CAR_RESERVATION = "http:localhost:8080/tcs-cars-service/ExternalCarRentalService";
    public static final String FLIGHT_RESERVATION = "http:localhost:8080/tcs-service-document/registry";
    public static final String HOTEL_SEARCH = "http:localhost:8080/hotels-service/ExternalHotelFinderService";
}
