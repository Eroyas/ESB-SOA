package fr.unice.polytech.esb.flows.CarServices;

import fr.unice.polytech.esb.flows.CarServices.data.CarRequest;
import fr.unice.polytech.esb.flows.CarServices.utils.CarServicesAggregationStrategy;
import fr.unice.polytech.esb.flows.flight.data.FlightInformation;
import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Eroyas on 14/10/17.
 */
public class CallCarPartners extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {

        /****************************************************************
         **             Error handler : if does not respond            **
         ****************************************************************/

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                .process(exchange -> exchange.getIn().setBody(new ArrayList<FlightInformation>()));

        /****************************************************************
         **          Car reservation (aggregation of services)         **
         ****************************************************************/

        from(CAR_RESERVATION_Q)
                .routeId("car-reservation-call")
                .routeDescription("Call the car reservation service")

                .log("\n###\n Request car reservation services \n###\n")

                // .unmarshal().json(JsonLibrary.Jackson, CarRequest.class)

                .multicast(new CarServicesAggregationStrategy())
                    .parallelProcessing(true)
                    .executorService(WORKERS)
                    .timeout(1000)
                    .to(CAR_EXTERNAL_RESERVATION_Q, CAR_INTERNAL_RESERVATION_Q)
                    .end()

                // .process(result2filteredCarByServices)

                .log("\n###\n Car: ${body} \n###\n")

                .setHeader("Content-Type", constant("application/json"))

                // .marshal().json(JsonLibrary.Jackson)
        ;
    }

    private static Processor result2filteredCarByServices = (Exchange exc) -> {
        Object response = exc.getIn().getBody();

        exc.getIn().setBody(response);
    };

}
