package scenarios.flightres;


import cucumber.api.java.en.*;
import dockerized.DockerizedTest;
import stubs.tta.*;

import javax.xml.ws.BindingProvider;
import java.net.URL;

import static org.junit.Assert.*;


public class FlightReservationStepDefinition extends DockerizedTest{

    private String host = getDockerHost();
    private int port = 9090;

    private String startingPoint;
    private String endingPoint;
    private String customerId;
    private String date;
    private float price;
    private String method;

    private FlightReservation_Type response;

    @Given("^The Flight reservation service deployed on (.*):(\\d+)$")
    public void select_host_and_port(String host, int port) { this.host = host; this.port = port; }

    @Given("^a customer identified as (.*)$")
    public void declare_a_customer(String identifier) { this.customerId = identifier; }

    @Given("^a starting point defined as (.*)$")
    public void declare_a_starting_point(String country) { this.startingPoint = country; }

    @Given("^an ending point defined as (.*)$")
    public void declare_an_ending_point(String country) { this.endingPoint = country; }

    @Given("^a date of departure defined as: (.+)$")
    public void declare_an_income(String date) { this.date = date; }

    @When("^the (simple|complex) reservation method is selected$")
    public void select_reservation_method(String method) { this.method = method; }

    @When("^the service is called$")
    public void call_service() {
        assertTrue("Unknown method",this.method.equals("simple") || this.method.equals("complex"));
        FlightReservation frs = getWS();
        if(this.method.equals("simple")) {
            SimpleItineraryRequest request = new SimpleItineraryRequest();
            request.setId(this.customerId);
            request.setOriginCountry(this.startingPoint);
            request.setDestinationCountry(this.endingPoint);
            request.setDepartureTime(this.date);

            this.response = frs.simpleReservation(request);
        }
    }

    @Then("^the flight price is (\\d+\\.\\d+)$")
    public void validate_flight_price(float value) {
        assertEquals(value, this.response.getPrice(), value);
    }

    @Then("^the booking date is set$")
    public void booking_date_is_set() {
        assertNotNull(this.response.getDate());
    }

    @Then("^the answer is associated to (.*)$")
    public void right_identifier_in_the_response(String identifier) {
        assertEquals(identifier,this.response.getIdentifier());
    }

    private FlightReservation getWS() {
        URL wsdl = FlightReservationStepDefinition.class.getResource("FlightReservationService.wsdl");
        FlightBookingService factory = new FlightBookingService(wsdl);
        FlightReservation ws = factory.getExternalFlightBookingPort();
        String address = "http://"+this.host+":"+this.port+"/tta-service-rpc/FlightBookingService";
        ((BindingProvider) ws).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        return ws;
    }
}