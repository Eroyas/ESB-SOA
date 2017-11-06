package fr.unice.polytech.esb.flows.utils;

/**
 * Created by Eroyas on 14/10/17.
 */
public class Endpoints {

    // Internal message queues
    public static final String MESSAGE_GENERATION = "activemq:message-generation";
    public static final String CAR_RESERVATION_Q = "activemq:car-reservation";
    public static final String CAR_EXTERNAL_RESERVATION_Q = "activemq:car-external-reservation";
    public static final String CAR_INTERNAL_RESERVATION_Q = "activemq:car-internal-reservation";
    public static final String FLIGHT_RESERVATION_Q = "activemq:flight-reservation";
    public static final String HOTEL_RESERVATION_Q = "activemq:hotel-reservation";

    // External partners
    public static final String CAR_RESERVATION = "http:localhost:8080/tcs-cars-service/ExternalCarRentalService";
    public static final String FLIGHT_RESERVATION = "http:localhost:8080/tcs-service-document/registry";
    public static final String HOTEL_SERVICE = "cxf:bean:HotelServiceEndpoint";

    public static final String CAR_RENTAL = "http:localhost:8080/tta-car-and-hotel/car-rental";

}
