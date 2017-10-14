package fr.unice.polytech.esb.flows.utils;

import fr.unice.polytech.esb.flows.data.CarInfo;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Eroyas on 14/10/17.
 */
public class CarReservationHelper {

    private XPath xpath = XPathFactory.newInstance().newXPath();

    public String buildRequest(CarInfo car, String uuid) {
        StringBuilder builder = new StringBuilder();

        builder.append("<cook:simple xmlns:cook=\"http://cookbook.soa1.polytech.unice.fr/\">\n");
        builder.append("  <CarInfo>\n");
        builder.append("    <id>" + uuid + "</id>\n");
        builder.append("    <brand>" + car.getBrand() + "</brand>\n");
        builder.append("    <model>" + car.getModel() + "</model>\n");
        builder.append("    <place>" + car.getPlace() + "</place>\n");
        builder.append("    <rentPricePerDay>" + car.getRentPricePerDay() + "</rentPricePerDay>\n");
        builder.append("    <availability>" + car.getAvailability() + "</availability>\n");
        builder.append("  </CarInfo>\n");
        builder.append("</cook:simple>");

        return builder.toString();
    }

}
