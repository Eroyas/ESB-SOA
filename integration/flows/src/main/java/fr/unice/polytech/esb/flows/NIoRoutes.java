package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.data.TravelPlan;
import fr.unice.polytech.esb.flows.utils.CsvTravelPlanFormat;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.json.JSONObject;

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
                    //.inOut(HOTEL_RESERVATION_Q)
                    //.inOut(CAR_RESERVATION_Q)
                    .inOut(SEARCH_FLIGHT)
                    .end()
                .process(exchange -> {
                    System.out.println("Process exchange: " + exchange.getIn().getBody());
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
            // Join result from internal and external flight services

            if (oldExchange != null) {
                JSONObject newBooking = new JSONObject(newExchange.getIn().getBody(String.class)).getJSONObject("booking");
                String newTypeOfBooking = newBooking.keys().next();
                JSONObject newBookingValue = newBooking.getJSONObject(newTypeOfBooking);

                JSONObject oldBooking = new JSONObject(oldExchange.getIn().getBody(String.class));
                oldBooking.getJSONObject("booking").put(newTypeOfBooking, newBookingValue);
                newExchange.getIn().setBody(oldBooking);
            }

            return newExchange;
        }
    }
}
