package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.data.TravelPlan;
import fr.unice.polytech.esb.flows.utils.CsvTravelPlanFormat;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

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
                .multicast()
                    .parallelProcessing().executorService(WORKERS)
                    //TODO: aggregate result
                    .to(SEARCH_FLIGHT, HOTEL_RESERVATION_Q);

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
}
