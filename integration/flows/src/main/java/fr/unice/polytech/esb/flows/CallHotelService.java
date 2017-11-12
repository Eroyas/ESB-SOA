package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.aggregation.HotelAggregationStrategy;
import fr.unice.polytech.esb.flows.data.HotelReservation;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import stubs.hotelservice.Hotel;
import stubs.hotelservice.HotelFinderService;
import stubs.hotelservice.HotelServiceClientFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class CallHotelService extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(2);

    @Override
    public void configure() throws Exception {

        from(HOTEL_RESERVATION_Q)
                .routeId("search-matching-hotels")
                .routeDescription("Search the hotels available for a given destination")
                .setProperty("destination", simple("${body.paysArrive}"))
                .setProperty("duration", simple("${body.durationInDay}"))
                .log("Calling Webservice for hotel reservation search with destination : ${exchangeProperty[destination]} and duration : ${exchangeProperty[duration]}")
                //.multicast(new HotelAggregationStrategy())
                    //.parallelProcessing(true)
                    //.executorService(WORKERS)
                    //.timeout(2000)
                    .to(HOTEL_SERVICE_EXTERNAL);
                    //.end();



        from(HOTEL_SERVICE_EXTERNAL)
                .routeId("hotel-reservation-external")
                .setHeader("operation_name", simple("recherche"))
                .process(callExternalHotelWebService)

                .log("\n\nHotel: ${body}\n\n")
                // Marshal the body into a json array.
                .setHeader("Content-Type", constant("application/json"));

        from(HOTEL_SERVICE_INTERNAL)
                .routeId("hotel-reservation-internal")
                .log(simple("${exchangeProperty[destination]}").getText())
                .process(callInternalHotelWebService)
                .log("Internal OK");
    }

    private static Processor callExternalHotelWebService = (Exchange exchange) -> {

        System.out.println("HELLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

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
                hotelReservation.setName(cheapest.getNom());
                hotelReservation.setPrice(cheapest.getPrix());
                hotelReservation.setRoom(10);

                exchange.setProperty("hotelReservation", hotelReservation);
            }

        }
    };

    private static Processor callInternalHotelWebService = (Exchange exchange) -> {
        // J'ai essayé avec les connecteur http, mais ça marchait pas
        String destination = exchange.getProperty("destination", String.class);
        Client client = ClientBuilder.newClient();

        Response response = client.target("http://internal-cars-service:8080/tta-car-and-hotel")
                .path("/hotels/city/" + destination)
                .request()
                .get();

        String raw = response.readEntity(String.class);
        JSONObject json = new JSONObject(raw);

        JSONArray hotels = json.getJSONArray("hotels");
        if (hotels.length() > 0) {
            JSONObject cheapest = hotels.getJSONObject(0);
            for(int i =1;i<hotels.length();i++) {
                JSONObject current = hotels.getJSONObject(i);
                if(current.getInt("price_per_night")<cheapest.getInt("price_per_night")) {
                    cheapest = current;
                }
            }
            HotelReservation reservation = new HotelReservation();
            reservation.setName(cheapest.getString("hotel_name"));
            reservation.setPrice(cheapest.getDouble("price_per_night"));
            reservation.setRoom(11);

            exchange.setProperty("hotelReservation", reservation);
        } else {
            exchange.setProperty("hotelReservation", null);
        }

        client.close();
    };



}
