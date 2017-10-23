package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.utils.CarReservationHelper;
import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created by Eroyas on 14/10/17.
 */
public class CallCarExternalPartners extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /****************************************************************
         **                Car reservation (a RPC service)             **
         ****************************************************************/

        from(CAR_RESERVATION_Q)
                .routeId("car-reservation-call")
                .routeDescription("Call the car reservation service : getCarRentalList(place, duration)")

                .bean(CarReservationHelper.class, "buildRequest(${body}, ${header[duration]})")

                .inOut(CAR_RESERVATION)
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
