package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class CallFlightExternalPartnersTest extends ActiveMQTest{

    @Override public String isMockEndpointsAndSkip() { return Endpoints.FLIGHT_RESERVATION_Q; }


    @Override protected RouteBuilder createRouteBuilder() throws Exception {
        CallFlightExternalPartners routes = new CallFlightExternalPartners();

        return new RouteBuilder() {
            @Override public void configure() throws Exception {
                this.includeRoutes(routes);
            }
        };
    }

    private static final String flightsRegistry = "mock://" + Endpoints.FLIGHT_RESERVATION_Q;

    private String testRequest;

    @Test public void testExecutionContext() throws Exception {
        assertNotNull(context.hasEndpoint(Endpoints.FLIGHT_RESERVATION_Q));
        assertNotNull(context.hasEndpoint(flightsRegistry));
    }



    @Before public void initRequest() {
        testRequest = "{\n" +
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
    }

    @Before public void initMocks() {
        resetMocks();
        getMockEndpoint(flightsRegistry).whenAnyExchangeReceived((Exchange exc) -> {
            String template = "{\n" +
                    "  \"result\": [\n" +
                    "    {\n" +
                    "     \"origin\": \"Nice\",\n" +
                    "     \"destination\": \"Paris\",\n" +
                    "     \"date\": \"2017-08-14\",\n" +
                    "     \"time\": \"12-30-00\",\n" +
                    "     \"price\": \"89\",\n" +
                    "     \"journeyType\": \"DIRECT\",\n" +
                    "     \"duration\": 92,\n" +
                    "     \"category\": \"ECO\",\n" +
                    "     \"airline\": \"Air France\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "     \"origin\": \"Nice\",\n" +
                    "     \"destination\": \"Paris\",\n" +
                    "     \"date\": \"2017-08-14\",\n" +
                    "     \"time\": \"08-45-00\",\n" +
                    "     \"price\": \"63\",\n" +
                    "     \"journeyType\": \"DIRECT\",\n" +
                    "     \"duration\": 105,\n" +
                    "     \"category\": \"ECO\",\n" +
                    "     \"airline\": \"EasyJet\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            exc.getIn().setBody(template);
        });
    }

    @Test public void testCallFlightExternalPartner() throws Exception {
        getMockEndpoint(flightsRegistry).expectedMessageCount(1);
        getMockEndpoint(flightsRegistry).expectedHeaderReceived("Content-Type", "application/json");
        getMockEndpoint(flightsRegistry).expectedHeaderReceived("Accept", "application/json");
        getMockEndpoint(flightsRegistry).expectedHeaderReceived("CamelHttpMethod", "POST");

    }

    @Test public void testCitizenRegistration() throws Exception {

        // Sending info for flight reservation
        template.sendBody(Endpoints.FLIGHT_RESERVATION_Q, testRequest);

        getMockEndpoint(flightsRegistry).assertIsSatisfied();

        // As the assertions are now satisfied, one can access to the contents of the exchanges
        String request = getMockEndpoint(flightsRegistry).getReceivedExchanges().get(0).getIn().getBody(String.class);

        String expected = "{\n" +
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

        JSONAssert.assertEquals(expected, testRequest, false);
    }

    @Test public void testFlightPartnerResponse() throws Exception {
        // Assertions on the mocks w.r.t. number of exchnaged messages
        getMockEndpoint(flightsRegistry).expectedMessageCount(1);

        // Calling the integration flow
        String out = (String) template.requestBody(Endpoints.FLIGHT_RESERVATION_Q, testRequest);

        // ensuring assertions
        String expectedResponse = "{\n" +
                "  \"result\": [\n" +
                "    {\n" +
                "     \"origin\": \"Nice\",\n" +
                "     \"destination\": \"Paris\",\n" +
                "     \"date\": \"2017-08-14\",\n" +
                "     \"time\": \"12-30-00\",\n" +
                "     \"price\": \"89\",\n" +
                "     \"journeyType\": \"DIRECT\",\n" +
                "     \"duration\": 92,\n" +
                "     \"category\": \"ECO\",\n" +
                "     \"airline\": \"Air France\"\n" +
                "    },\n" +
                "    {\n" +
                "     \"origin\": \"Nice\",\n" +
                "     \"destination\": \"Paris\",\n" +
                "     \"date\": \"2017-08-14\",\n" +
                "     \"time\": \"08-45-00\",\n" +
                "     \"price\": \"63\",\n" +
                "     \"journeyType\": \"DIRECT\",\n" +
                "     \"duration\": 105,\n" +
                "     \"category\": \"ECO\",\n" +
                "     \"airline\": \"EasyJet\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        assertEquals(expectedResponse, out);
    }
}
