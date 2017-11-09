package fr.unice.polytech.esb.flows.flight;

import fr.unice.polytech.esb.flows.data.TravelPlan;
import fr.unice.polytech.esb.flows.flight.data.FlightInformation;
import fr.unice.polytech.esb.flows.flight.data.FlightRequest;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

/**
 * Created by GNINGK on 16/10/17.
 */
public class FlightSearchEngine extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {

        // If the service does not respond, fill the body with an empty list.
        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                .process(exchange -> exchange.getIn().setBody(new ArrayList<FlightInformation>()));


        from(SEARCH_FLIGHT)
                .routeId("flight-reservation-call")
                .routeDescription("Retrieve cheapest flight available depending on user criteria")

                // Send the request to flight request to the internal and external flight service.
                // All requests are awaited thanks to "parallelProcessing".
                // The result is given as an aggregated list composed of the response from the internal and external services
                .multicast(new FlightAggregationStrategy())
                    .parallelProcessing(true)
                    .executorService(WORKERS)
                    .timeout(1000)
                    .to(USE_INTERNAL_FLIGHT_RESERVATION, USE_EXTERNAL_FLIGHT_RESERVATION)
                    .end()

                // Select the cheapest flight from aggregated list.
                .process(exchange -> {
                    List<FlightInformation> flights = (List<FlightInformation>) exchange.getIn().getBody(List.class);

                    if (flights == null || flights.isEmpty()) {
                        exchange.getIn().setBody("{}");
                    } else {
                        System.out.println("\n\nGot " + flights.size() + " result from flights search partners");
                        flights.sort(Comparator.comparingDouble(FlightInformation::getPrice));
                        FlightInformation cheapestFlight = flights.get(0);
                        System.out.println("Cheapest flight is: " + cheapestFlight.toString() + "\n\n");
                        exchange.getIn().setBody(cheapestFlight);
                    }
                })


                // Marshal the body into a json array.
                .setHeader("Content-Type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);

    }



    private class FlightAggregationStrategy implements AggregationStrategy {

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            // Join result from internal and external flight services
            if (oldExchange != null) {
                newExchange.getIn().getBody(List.class)
                        .addAll(oldExchange.getIn().getBody(List.class));
            }

            return newExchange;
        }
    }
}