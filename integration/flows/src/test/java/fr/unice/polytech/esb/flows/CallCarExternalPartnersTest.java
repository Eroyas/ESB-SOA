package fr.unice.polytech.esb.flows;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Eroyas on 15/10/17.
 */
public class CallCarExternalPartnersTest extends ActiveMQTest {

    @Override public String isMockEndpointsAndSkip() {
        return CAR_RESERVATION;
    }

    @Override public String isMockEndpoints() {
        return CAR_RESERVATION_Q;
    }

    @Test public void testExecutionContext() throws Exception {
        isAvailableAndMocked(CAR_RESERVATION);
        isAvailableAndMocked(CAR_RESERVATION_Q);
    }

    private String carRequest;

    @Before public void initRequest() {
        carRequest = "" +
                "<soa:getCarRentalList>\n" +
                "    <place>France</place>\n" +
                "    <duration>2</duration>\n" +
                "</soa:getCarRentalList>";
    }

    @Before public void initMocks() {
        resetMocks();

        mock(CAR_RESERVATION).whenAnyExchangeReceived((Exchange e) -> {
            String response = "" +
                    "<ns2:getCarRentalListResponse xmlns:ns2=\"http://informatique.polytech.unice.fr/soa/\">\n" +
                    "    <car_rentals>\n" +
                    "        <availability>2</availability>\n" +
                    "        <brand>MINI</brand>\n" +
                    "        <model>Cooper</model>\n" +
                    "        <place>France</place>\n" +
                    "        <rentPricePerDay>34</rentPricePerDay>\n" +
                    "    </car_rentals>\n" +
                    "    <car_rentals>\n" +
                    "        <availability>10</availability>\n" +
                    "        <brand>Lincoln</brand>\n" +
                    "        <model>Zephyr</model>\n" +
                    "        <place>France</place>\n" +
                    "        <rentPricePerDay>254</rentPricePerDay>\n" +
                    "    </car_rentals>\n" +
                    "</ns2:getCarRentalListResponse>";

            e.getIn().setBody(response);
        });
    }

    @Test public void testCar() throws Exception  {
        mock(CAR_RESERVATION_Q).expectedMessageCount(1);
        mock(CAR_RESERVATION).expectedMessageCount(1);

        String out = template.requestBodyAndHeader(
                CAR_RESERVATION_Q, "Paris",
                "duration", "2",
                String.class);

/*  Version 3 : local test and with json converter and filter */

        String expectedResponse = "" +
                "{\n" +
                "    \"model\": \"Cooper\",\n" +
                "    \"availability\": 2,\n" +
                "    \"place\": \"France\",\n" +
                "    \"brand\": \"MINI\",\n" +
                "    \"rentPricePerDay\": 34\n" +
                "}";

        assertEquals(expectedResponse, out);

/*
        assertMockEndpointsSatisfied(1, TimeUnit.SECONDS);
        InputSource src =  new InputSource(new StringReader(out));
        assertTrue((boolean) xpath.evaluate("/", src, XPathConstants.BOOLEAN));
*/

    }

}

/* Version 1 : local test and without json converter

    String expectedResponse = "" +
            "<ns2:getCarRentalListResponse xmlns:ns2=\"http://informatique.polytech.unice.fr/soa/\">\n" +
            "    <car_rentals>\n" +
            "        <availability>2</availability>\n" +
            "        <brand>MINI</brand>\n" +
            "        <model>Cooper</model>\n" +
            "        <place>France</place>\n" +
            "        <rentPricePerDay>34</rentPricePerDay>\n" +
            "    </car_rentals>\n" +
            "    <car_rentals>\n" +
            "        <availability>10</availability>\n" +
            "        <brand>Lincoln</brand>\n" +
            "        <model>Zephyr</model>\n" +
            "        <place>France</place>\n" +
            "        <rentPricePerDay>254</rentPricePerDay>\n" +
            "    </car_rentals>\n" +
            "</ns2:getCarRentalListResponse>";

    assertEquals(expectedResponse, out);
*/

/* Version 2 local test and with json converter

    String expectedResponse = "" +
        "{\"ns2:getCarRentalListResponse\": {\n" +
        "    \"xmlns:ns2\": \"http://informatique.polytech.unice.fr/soa/\",\n" +
        "    \"car_rentals\": [\n" +
        "        {\n" +
        "            \"model\": \"Cooper\",\n" +
        "            \"availability\": 2,\n" +
        "            \"place\": \"France\",\n" +
        "            \"brand\": \"MINI\",\n" +
        "            \"rentPricePerDay\": 34\n" +
        "        },\n" +
        "        {\n" +
        "            \"model\": \"Zephyr\",\n" +
        "            \"availability\": 10,\n" +
        "            \"place\": \"France\",\n" +
        "            \"brand\": \"Lincoln\",\n" +
        "            \"rentPricePerDay\": 254\n" +
        "        }\n" +
        "    ]\n" +
        "}}";

    assertEquals(expectedResponse, out);

*/
