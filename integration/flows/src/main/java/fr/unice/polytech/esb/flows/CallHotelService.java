package fr.unice.polytech.esb.flows;

import org.apache.camel.builder.RouteBuilder;
import stubs.hotelservice.Hotel;
import stubs.hotelservice.HotelFinderService;
import stubs.hotelservice.HotelServiceClientFactory;

import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;
import static fr.unice.polytech.esb.flows.utils.Config.*;

public class CallHotelService extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from(HOTEL_RESERVATION_Q)
                .routeId("search-matching-hotels")
                .routeDescription("Search the hotels available for a given destination")

                .setProperty("destination", simple("${body.destination}"))
                .setProperty("duration", simple("${body.duration}"))
                .log("Calling Webservice for hotel reservation search with destination : ${exchangeProperty[destination]} and duration : ${exchangeProperty[duration]}");
                //TODO
    }
}
