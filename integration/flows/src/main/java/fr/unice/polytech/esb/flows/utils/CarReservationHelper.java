package fr.unice.polytech.esb.flows.utils;

/**
 * Created by Eroyas on 14/10/17.
 */
public class CarReservationHelper {

    public String buildRequest(String place, int duration) {
        StringBuilder builder = new StringBuilder();

        builder.append("<info:getCarRentalList xmlns:info=\"http://informatique.polytech.unice.fr/soa/\">\n");
        builder.append("  <place>" + place + "</place>\n");
        builder.append("  <duration>" + duration + "</duration>\n");
        builder.append("</info:getCarRentalList>");

        return builder.toString();
    }

}
