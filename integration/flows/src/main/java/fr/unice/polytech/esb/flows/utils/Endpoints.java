package fr.unice.polytech.esb.flows.utils;

/**
 * Created by Eroyas on 14/10/17.
 */
public class Endpoints {

    // Internal message queues
    public static final String CAR_RESERVATION_Q = "activemq:car-reservation";

    // External partners
    public static final String CAR_RESERVATION = "http://localhost:8080/tcs-cars-service/ExternalCarRentalService";
}
