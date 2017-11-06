package fr.unice.polytech.esb.flows;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
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
                .setProperty("destination", simple("${body.paysArrive}"))
                .setProperty("duration", simple("${body.durationInDay}"))
                .log("Calling Webservice for hotel reservation search with destination : ${exchangeProperty[destination]} and duration : ${exchangeProperty[duration]}")
                .inOut(HOTEL_SERVICE);

        from(HOTEL_SERVICE)
                .setHeader("operation_name",simple("recherche"))
                .process(callHotelWebService);

    }

    private static Processor callHotelWebService = (Exchange exchange) -> {

        HotelFinderService hotelFinderService = HotelServiceClientFactory.getInstance("hotels-services", "8080");

        Message inMessage = exchange.getIn();
        String operationName = inMessage.getHeader("operation_name", String.class);

        if ("recherche".equals(operationName)) {
            String destination = exchange.getProperty("destination",String.class);
            int duration = exchange.getProperty("duration", Integer.class);
            System.out.println("Calling recherche with argument destination : "+destination+" and duration : "+duration);
            List<Hotel> hotels = hotelFinderService.recherche(destination, duration, false);
            System.out.println("Got result from recherche : "+ hotels.size());
        }
    };

}
