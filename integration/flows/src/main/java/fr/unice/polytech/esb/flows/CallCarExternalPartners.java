package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.data.CarInfo;
import fr.unice.polytech.esb.flows.utils.CarReservationHelper;
import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;

import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Eroyas on 14/10/17.
 */
public class CallCarExternalPartners extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /****************************************************************
         **                Car reservation (a RPC service)             **
         ****************************************************************/

        from(CAR_RESERVATION_Q)
                .routeId("car-reservation-call")
                .routeDescription("Call the car reservation service : getCarRentalList(place, duration)")

                .bean(CarReservationHelper.class, "buildRequest(${body}, ${header[duration]})")

                .inOut(CAR_RESERVATION)
                //.process(result2carInfo)
        ;
    }

    private static Processor result2carInfo = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        Source response = (Source) exc.getIn().getBody();
        CarInfo result = new CarInfo(exc.getProperty("partial-car-info", CarInfo.class));
        exc.getIn().setBody(result);
    };

}
