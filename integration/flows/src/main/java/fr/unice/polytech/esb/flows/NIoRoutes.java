package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.data.Booking;
import fr.unice.polytech.esb.flows.data.HotelReservation;
import fr.unice.polytech.esb.flows.data.TravelPlan;
import fr.unice.polytech.esb.flows.utils.CsvTravelPlanFormat;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class  NIoRoutes extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(10);

    @Override
    public void configure() throws Exception {
        from(CSV_INPUT_TRAVEL_PLAN)
                .routeId("csv-to-exchange-message")
                .routeDescription("Load a CSV file containing travel plans search informations")

                .log("Starting automated search")
                .unmarshal(CsvTravelPlanFormat.buildFormat())
                .log("Decomposing file content")
                .split(body())
                    .process(csv2TravelPlanData)
                    .log("Travel Plan object built ! [PDep: ${body.paysDepart}, PArr: ${body.paysArrive}, duration: ${body.durationInDay}]")

                .multicast(new BookingAggregationStrategy())
                    .parallelProcessing(true)
                    .executorService(WORKERS)
                    .timeout(4000)
                    .stopOnException()
                    .to(HOTEL_RESERVATION_Q)
                    //.inOut(CAR_RESERVATION_Q)
                    //.inOut(SEARCH_FLIGHT)
                    .end()
                .process(exchange -> {
                    HotelReservation hr = exchange.getIn().getBody(HotelReservation.class);
                    System.out.println("hotel reservation : " + hr);
                    System.out.println("hotelReservation : "+ exchange.getProperty("hotelReservation"));
                    HotelReservation theHotelReservation = exchange.getProperty("hotelReservation", HotelReservation.class);
                    Booking theBooking = new Booking();
                    theBooking.setHotelReservation(theHotelReservation);
                    exchange.getIn().setBody(theBooking);
                })
                .log("\n\nBooking to send: ${body}\n\n")
                .to(BOOKING_SERVICE);
    }

    private static Processor csv2TravelPlanData = (Exchange exchange) -> {
        Map<String, Object> data = (Map<String, Object>) exchange.getIn().getBody();
        TravelPlan tp = new TravelPlan();
        tp.setPaysDepart((String) data.get("PaysDepart"));
        tp.setPaysArrive((String) data.get("PaysArrive"));
        tp.setDateDepart((String) data.get("DateDepart"));

        String durationInDays = (String) data.get("duration");
        int duration = 1;
        try {
            duration = Integer.parseInt(durationInDays);
        } catch (NumberFormatException nfe) {
            System.err.print("Expected duration to be an int, was not the case. Defaulting value to 1 day.");
            duration=1;
        }
        tp.setDurationInDay(duration);
        exchange.getIn().setBody(tp);
    };

    private class BookingAggregationStrategy implements AggregationStrategy {

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (oldExchange != null) {
                Map<String, Object> one = oldExchange.getProperties();
                if (newExchange != null) {
                    for (Map.Entry<String, Object> property : one.entrySet()) {
                        newExchange.setProperty(property.getKey(), property.getValue());
                        System.out.println("property ["+property.getKey()+"] added");
                    }
                    return newExchange;
                }
                return oldExchange;
            }

            return newExchange;
        }
    }
}
