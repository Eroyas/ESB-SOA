package fr.unice.polytech.esb.flows.utils;

import fr.unice.polytech.esb.flows.data.CarInfo;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Eroyas on 14/10/17.
 */
public class CarReservationHelper {

    private XPath xpath = XPathFactory.newInstance().newXPath();

    public String buildRequest(String place, int duration) {
        StringBuilder builder = new StringBuilder();

        builder.append("<info:getCarRentalList xmlns:info=\"http://informatique.polytech.unice.fr/soa/\">\n");
        builder.append("  <place>" + place + "</place>\n");
        builder.append("  <duration>" + duration + "</duration>\n");
        builder.append("</info:getCarRentalList>");

        return builder.toString();
    }

}
