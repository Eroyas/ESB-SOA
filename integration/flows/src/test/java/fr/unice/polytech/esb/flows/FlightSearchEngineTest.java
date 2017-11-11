package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.flight.FlightSearchEngine;
import fr.unice.polytech.esb.flows.flight.data.FlightRequest;
import fr.unice.polytech.esb.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.apache.camel.builder.AdviceWithRouteBuilder;

public class FlightSearchEngineTest extends ActiveMQTest{

    // Define mock endpoint to be used
    @Override public String isMockEndpointsAndSkip() {
        return Endpoints.USE_INTERNAL_FLIGHT_RESERVATION +
                "|" + Endpoints.USE_EXTERNAL_FLIGHT_RESERVATION;
    }

    // Create routes
    @Override protected RouteBuilder createRouteBuilder() throws Exception {
        FlightSearchEngine flightSearchEngineRoutes = new FlightSearchEngine();

        return new RouteBuilder() {
            @Override public void configure() throws Exception {
                this.includeRoutes(flightSearchEngineRoutes);
            }
        };
    }

    // Configuring expectations on the mocked endpoint
    private static final String internal_flight_search_engine = "mock:" + Endpoints.USE_INTERNAL_FLIGHT_RESERVATION;
    private static final String external_flight_search_engine = "mock:" + Endpoints.USE_EXTERNAL_FLIGHT_RESERVATION;

    private String flightRequest;

    @Before public void initRequest() {
        flightRequest = "{\n" +
                "   \"origin\": \"Nice\",\n" +
                "   \"destination\": \"Paris\",\n" +
                "   \"date\": \"2017-08-14\",\n" +
                "   \"timeFrom\": \"08-00-00\",\n" +
                "   \"timeTo\": \"14-30-00\",\n" +
                "   \"journeyType\": \"DIRECT\",\n" +
                "   \"maxTravelTime\": 120,\n" +
                "   \"category\": \"ECO\",\n" +
                "   \"airline\": \"Air France\",\n" +
                "   \"order\": \"ASCENDING\"\n" +
                "}";

        try {
            System.out.println("\n\nTest route definition: " + this.context.getRouteDefinitions().get(0) + "\n\n");
            this.context.getRouteDefinition("flight-reservation-call").adviceWith(this.context, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    mockEndpoints();
                    //interceptSendToEndpoint(Endpoints.USE_EXTERNAL_FLIGHT_RESERVATION).to(external_flight_search_engine);
                    //interceptSendToEndpoint(Endpoints.USE_INTERNAL_FLIGHT_RESERVATION).to(internal_flight_search_engine);
                }});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before public void initMocks() {
        resetMocks();
        getMockEndpoint(internal_flight_search_engine).whenAnyExchangeReceived((Exchange exc) -> {
            FlightRequest template = (FlightRequest) exc.getIn().getBody();
            System.out.println("\n\nFrom mock internal flight search engine:\n" + template.getDate() + "\n\n\n");
            exc.getIn().setBody(template);
        });
        getMockEndpoint(external_flight_search_engine).whenAnyExchangeReceived((Exchange exc) -> {
            String template = exc.getIn().getBody().toString();
            System.out.println("\n\n\n" + template + "\n\n\n");
            exc.getIn().setBody(template);
        });
    }

    // Asserting endpoints existence
    @Test public void testExecutionContext() throws Exception{
        System.out.println("\n\nTest execution context:\n\n");

        assertNotNull(context.hasEndpoint(Endpoints.SEARCH_FLIGHT));
        assertNotNull(context.hasEndpoint(Endpoints.USE_INTERNAL_FLIGHT_RESERVATION));
        assertNotNull(context.hasEndpoint(Endpoints.USE_EXTERNAL_FLIGHT_RESERVATION));
        assertNotNull(context.hasEndpoint(internal_flight_search_engine));
        assertNotNull(context.hasEndpoint(external_flight_search_engine));
    }

    /*@Test public void testCallFlightSearchEngine() throws Exception {
        System.out.println("\n\nTest call flight search engine:\n\n");

        getMockEndpoint(internal_flight_search_engine).expectedMessageCount(1);
        getMockEndpoint(internal_flight_search_engine).expectedHeaderReceived("Content-Type", "application/soap+xml");
        getMockEndpoint(internal_flight_search_engine).expectedHeaderReceived("Accept", "application/json");
        getMockEndpoint(internal_flight_search_engine).expectedHeaderReceived("CamelHttpMethod", "POST");

        getMockEndpoint(external_flight_search_engine).expectedMessageCount(1);
        getMockEndpoint(external_flight_search_engine).expectedHeaderReceived("Content-Type", "application/json");
        getMockEndpoint(external_flight_search_engine).expectedHeaderReceived("Accept", "application/json");
        getMockEndpoint(external_flight_search_engine).expectedHeaderReceived("CamelHttpMethod", "POST");
    }*/

    @Test public void testFlightRequest() throws Exception {

        String exampleReceivedInternal = "";
        String exampleReceivedExternal = "";

        System.out.println("\n\nTest flight request:\n\n");
        MockEndpoint mock = getMockEndpoint(internal_flight_search_engine);
        mock.setRetainFirst(5);
        template.sendBody(Endpoints.SEARCH_FLIGHT, flightRequest);

        mock.assertIsSatisfied();
        getMockEndpoint(external_flight_search_engine).assertIsSatisfied();

        //int internalReceivedExchangeLength = getMockEndpoint(internal_flight_search_engine).getReceivedExchanges().size();
        //int externalReceivedExchangeLength = getMockEndpoint(external_flight_search_engine).getReceivedExchanges().size();
        String internalReceivedExchange = mock.getReceivedExchanges().get(0).getIn().getBody(String.class);
        //String externalReceivedExchange = getMockEndpoint(external_flight_search_engine).getReceivedExchanges().get(0).getIn().getBody(String.class);

        //System.out.println(internalReceivedExchange);
        //System.out.println(externalReceivedExchange);

        //JSONAssert.assertEquals(exampleReceivedInternal, internalReceivedExchange, false);
        //JSONAssert.assertEquals(exampleReceivedExternal, externalReceivedExchange, false);
    }

}
