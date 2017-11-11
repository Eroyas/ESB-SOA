package fr.unice.polytech.esb.flows.CarServices;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.esb.flows.data.TravelPlan;
import fr.unice.polytech.esb.flows.flight.data.FlightInformation;
import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.net.ConnectException;
import java.util.ArrayList;

/**
 * Created by Eroyas on 10/11/17.
 */
public class CallCarInternalPartners extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /****************************************************************
         **             Error handler : if does not respond            **
         ****************************************************************/

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                .process(exchange -> exchange.getIn().setBody(new ArrayList<FlightInformation>()));

        onException(ConnectException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                .log("Cannot connect to car internal service")
                .process(exchange -> exchange.getIn().setBody(new ArrayList<FlightInformation>()));

        /****************************************************************
         **          Our car reservation (a resource service)          **
         ****************************************************************/

        from(CAR_INTERNAL_RESERVATION_Q)
                .routeId("car-internal-reservation-call")
                .routeDescription("Call the internal car reservation service : listAgencyByCity(city)")

                .log("\n###\n Request for internal car reservation \n###\n")

                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))

                .process(exchange -> exchange.getIn()
                        .setBody(adaptRequest(exchange.getIn().getBody(TravelPlan.class))))

                .log("\n###\n Car internal service: ${body} \n###\n")

                .inOut(CAR_INTERNAL_RESERVATION)

                .log("\n###\n Car internal result: ${body} \n###\n")

                //.process(result2filteredCarByPrice)
        ;
    }

    private static String adaptRequest(TravelPlan carRequest) {
        String place = carRequest.getPaysArrive();

        // TODO convertir ville en pays

        return String.format(
                "/search/Nice",
                place);

        // return (CAR_INTERNAL_RESERVATION.replace("http:", "http://") + "/search/" + "Nice");
    }

    private static Processor result2filteredCarByPrice = (Exchange exc) -> {
        Object response = exc.getIn().getBody();

        exc.getIn().setBody(response);
    };

}
