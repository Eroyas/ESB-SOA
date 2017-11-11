package fr.unice.polytech.esb.flows.CarServices;

import fr.unice.polytech.esb.flows.CarServices.utils.CarReservationHelper;
import fr.unice.polytech.esb.flows.flight.data.FlightInformation;
import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;

/**
 * Created by Eroyas on 10/11/17.
 */
public class CallCarExternalPartner extends RouteBuilder {

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
         **                Car reservation (a RPC service)             **
         ****************************************************************/

        from(CAR_EXTERNAL_RESERVATION_Q)
                .routeId("car-external-reservation-call")
                .routeDescription("Call the external car reservation service : getCarRentalList(place, duration)")

                .log("\n###\n Request for external car reservation \n###\n")

                .bean(CarReservationHelper.class, "buildRequest(${body}, ${header[duration]})")

                .inOut(CAR_EXTERNAL_RESERVATION)

                .process(result2carJson)
                .process(result2filteredCarByPrice)
        ;

    }

    private static Processor result2carJson = (Exchange exc) -> {
        Object response = exc.getIn().getBody();

        try {
            JSONObject jObj = XML.toJSONObject(String.valueOf(response));
            String result = jObj.toString(4);

            exc.getIn().setBody(result);
        } catch (JSONException e) {
            System.out.println(e.toString());
        }
    };

    private static Processor result2filteredCarByPrice = (Exchange exc) -> {
        Object response = exc.getIn().getBody();

        JSONObject jObj = new JSONObject(response.toString());
        JSONObject obj = jObj.getJSONObject("ns2:getCarRentalListResponse");
        JSONArray rentals = (JSONArray) obj.get("car_rentals");

        int bestPrice = (int) rentals.getJSONObject(0).get("rentPricePerDay");
        int index = 0;

        for (int i = 0; i < rentals.length(); i++) {

            int price = (int) rentals.getJSONObject(i).get("rentPricePerDay");

            if (bestPrice > price) {
                bestPrice = price;
                index = i;
            }
        }

        String result = rentals.getJSONObject(index).toString(4);

        exc.getIn().setBody(result);
    };

}
