package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.utils.HotelSearcHelper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class CallHotelService extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from(CONSOLE_IN_ASK_DESTINATION)
                .routeId("search-matching-hotels")
                .routeDescription("Search the hotels available for a given destination")

                .setProperty("destination", simple("${body}"))
                .log("Calling Webservice for hotel reservation search with destination : ${exchangeProperty[destination]}")

                .bean(HotelSearcHelper.class, "buildRequest(${exchangeProperty[destination]")
                .inOut(HOTEL_SEARCH)
                .inOut(CONSOLE_OUTPUT);
    }
}
