package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.utils.TravelMessageGenerator;
import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

import org.apache.camel.builder.RouteBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Eroyas on 23/10/17.
 */
public class IoRoutes extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(10);

    @Override
    public void configure() throws Exception {

        from(MESSAGE_GENERATION)
                .routeId("message-generation")
                .routeDescription("Generate a message with all travel information")

                .setProperty("travel-info", simple("${body}"))
                .bean(TravelMessageGenerator.class,
                        "write(${body})")

                .multicast()
                    .parallelProcessing().executorService(WORKERS)
                    .to(CAR_RESERVATION_Q, FLIGHT_RESERVATION_Q, HOTEL_RESERVATION_Q)
        ;
    }

}
