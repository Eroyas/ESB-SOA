package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.data.HotelReservation;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.json.JSONObject;
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
                .routeId("hotel-reservation")
                .setHeader("operation_name", simple("recherche"))
                .process(callHotelWebService)

                .log("\n\nHotel: ${body}\n\n")
                // Marshal the body into a json array.
                .setHeader("Content-Type", constant("application/json"));

    }

    private static Processor callHotelWebService = (Exchange exchange) -> {

        HotelFinderService hotelFinderService = HotelServiceClientFactory.getInstance("hotels-services", "8080");

        Message inMessage = exchange.getIn();
        String operationName = inMessage.getHeader("operation_name", String.class);

        if ("recherche".equals(operationName)) {
            String destination = exchange.getProperty("destination",String.class);
            int duration = exchange.getProperty("duration", Integer.class);
            System.out.println("Calling recherche with argument destination : "+destination+" and duration : "+duration);
            List<Hotel> hotels = hotelFinderService.recherche(destination, duration, true);
            System.out.println("Got result from recherche : "+ hotels.size());
            if (hotels.size() > 0) {
                Hotel cheapest = hotels.get(0);
                System.out.println("Selecting lowest price : name: "+cheapest.getNom()+", price: "+cheapest.getPrix()+" location:"+cheapest.getLieu());

                HotelReservation hotelReservation = new HotelReservation();
                hotelReservation.setHotelName(cheapest.getNom());
                hotelReservation.setPrice(cheapest.getPrix());
                hotelReservation.setRoomNumber(10);

                exchange.getIn().setBody(hotelReservation.toString());
            }

        }
    };

}
