package fr.unice.polytech.esb.flows.flight;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.esb.flows.data.TravelPlan;
import fr.unice.polytech.esb.flows.flight.data.FlightInformation;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;


/**
 * Created by GNINGK on 16/10/17.
 */
public class CallFlightExternalPartners extends RouteBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        /*************************************************************************
         ** Flight reservation (a Document service implemented using REST/JSON) **
         *************************************************************************/
        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<FlightInformation>()));
        onException(ConnectException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                .log("Cannot connect to flight external service")
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<FlightInformation>()));
        from(USE_EXTERNAL_FLIGHT_RESERVATION)
                .routeId("flight-external-reservation-call")
                .routeDescription("Retrieve flight available depending of user criteria")


                .log("Creating retrieval request for flights using external flights service")

                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> exchange.getIn()
                        .setBody(adaptRequest(exchange.getIn().getBody(TravelPlan.class))))

                // Send the request to the internal service.
                .inOut(CALL_EXTERNAL_FLIGHT_RESERVATION)

                .process(exchange -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    JsonNode flightsNode = objectMapper
                            .readTree(exchange.getIn().getBody(String.class))
                            .get("result");

                    ObjectReader reader = objectMapper.readerFor(
                            new TypeReference<List<FlightInformation>>() {
                            });

                    List<FlightInformation> flightsInformation = reader.readValue(flightsNode);

                    exchange.getIn().setBody(flightsInformation);
                });

    }


    private static String adaptRequest(TravelPlan flightRequest) {
        ObjectNode json = mapper.createObjectNode();
        json.put("origin", "France"); //flightRequest.getPaysDepart());
        json.put("destination", "Norway");  //flightRequest.getPaysArrive());
        json.put("date", flightRequest.getDateDepart());
        json.put("timeFrom", "08:00:00");
        json.put("timeTo", "22:00:00");
        json.put("journeyType", "DIRECT");
        json.put("maxTravelTime", 120);
        json.put("category", "ECO");
        return json.toString();
    }



}