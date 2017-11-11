package fr.unice.polytech.esb.flows.CarServices;

import fr.unice.polytech.esb.flows.flight.data.FlightInformation;
import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

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

        /****************************************************************
         **          Our car reservation (a resource service)          **
         ****************************************************************/

        from(CAR_INTERNAL_RESERVATION_Q)
                .routeId("car-internal-reservation-call")
                .routeDescription("Call the internal car reservation service : listAgencyByCity(city)")

                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))

                // Using a dynamic endpoint => refer to a recipient list, inOut as an endpoint parameter
                .setBody(simple(""))

                .inOut(CAR_INTERNAL_RESERVATION)
                //.process(result2filteredCarByPrice)
        ;
    }

    private static Processor result2filteredCarByPrice = (Exchange exc) -> {
        Object response = exc.getIn().getBody();

        exc.getIn().setBody(response);
    };

}
