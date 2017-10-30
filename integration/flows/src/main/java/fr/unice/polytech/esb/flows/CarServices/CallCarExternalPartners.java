package fr.unice.polytech.esb.flows.CarServices;

import fr.unice.polytech.esb.flows.CarServices.utils.CarReservationHelper;
import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

import fr.unice.polytech.esb.flows.CarServices.utils.ServicesAggregationStrategy;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Eroyas on 14/10/17.
 */
public class CallCarExternalPartners extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(4);

    @Override
    public void configure() throws Exception {

        from(CAR_RESERVATION_Q)
                .routeId("car-reservation-call")
                .routeDescription("Call the car reservation service")

                .setProperty("car-reservation-info", simple("${body}"))
                /*.bean(CarReservationMessageGenerator.class,
                        "write(${body})")*/

                .multicast(new ServicesAggregationStrategy())
                    .parallelProcessing(true)
                    .executorService(WORKERS)
                    .timeout(1000)
                    .to(CAR_EXTERNAL_RESERVATION_Q, CAR_INTERNAL_RESERVATION_Q)
                    .end()
        ;

        /****************************************************************
         **                Car reservation (a RPC service)             **
         ****************************************************************/

        from(CAR_EXTERNAL_RESERVATION_Q)
                .routeId("car-external-reservation-call")
                .routeDescription("Call the external car reservation service : getCarRentalList(place, duration)")

                .bean(CarReservationHelper.class, "buildRequest(${body}, ${header[duration]})")

                .inOut(CAR_RESERVATION)
                .process(result2carJson)
                .process(result2filteredCarByPrice)
        ;

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
