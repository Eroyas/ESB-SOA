package fr.unice.polytech.esb.flows;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;


/**
 * Created by GNINGK on 16/10/17.
 */
public class CallFlightExternalPartners extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        /*************************************************************************
         ** Flight reservation (a Document service implemented using REST/JSON) **
         *************************************************************************/

        from(FLIGHT_RESERVATION_Q)
                .routeId("flight-reservation-call")
                .routeDescription("Retrieve flight available depending of user criteria")

                .setProperty("res-uuid", simple("${body}"))

                .setProperty("origin", simple("${body.origin}"))
                .setProperty("destination", simple("${body.destination}"))
                .setProperty("date", simple("${body.date}")) //formatted as year-month-day, e.g. 2017-01-21)
                .setProperty("timeFrom", simple("${body.timeFrom}"))
                .setProperty("timeTo", simple("${body.timeTo}"))
                .setProperty("journeyType", simple("${body.timeTo}")) // (DIRECT or INDIRECT)
                .setProperty("maxTravelTime", simple("${body.maxTravelTime}"))
                .setProperty("category", simple("${body.category}")) // (can be ECO, ECO_PREMIUM, BUSINESS, FIRST)
                .setProperty("airline", simple("${body.airline}"))
                .log("Creating retrieval request for flights #${exchangeProperty[res-uuid]}")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process((Exchange exchange) -> {
                    String request = "{\n" +
                            "  \"origin\": \"" + exchange.getProperty("origin", String.class) + "\",\n" +
                            "  \"destination\": \"" + exchange.getProperty("destination", String.class) + "\",\n" +
                            "  \"date\": \"" + exchange.getProperty("date", String.class) + "\",\n" +
                            "  \"timeFrom\": \"" + exchange.getProperty("timeFrom", String.class) + "\",\n" +
                            "  \"timeTo\": \"" + exchange.getProperty("timeTo", String.class) + "\",\n" +
                            "  \"journeyType\": \"Direct\"\n" +
                            "  \"maxTravelTime\": \"" + exchange.getProperty("maxTravelTime", Integer.class) + "\",\n" +
                            "  \"category\": \"FIRST\",\n" +
                            "  \"airline\": \"" + exchange.getProperty("airline", String.class) + "\"\n" +
                            "}";
                    exchange.getIn().setBody(request);
                })
                .inOut(FLIGHT_RESERVATION)
        ;
    }
}