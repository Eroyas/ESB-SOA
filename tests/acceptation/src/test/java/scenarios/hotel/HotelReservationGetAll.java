package scenarios.hotel;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dockerized.DockerizedTest;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;

import javax.ws.rs.core.MediaType;
import static org.junit.Assert.assertTrue;

/**
 * Created by Eroyas on 02/10/17.
 */
public class HotelReservationGetAll extends DockerizedTest{

    private String host = getDockerHost();
    private String serviceName = "/tta-car-and-hotel";
    private int port = 9080;

    private String endpoint;
    private String httpMethod;

    private String result;

    @Given("^the getAll hotel reservation service is up$")
    public void the_hotel_reservation_service_is_up() {

    }

    @When("^the getAll hotel endpoint is '(.*)'$")
    public void set_endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("^the getAll hotel select method is (.*)$")
    public void set_httpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    @When("^the getAll hotel request is sent$")
    public void call_service() {
        String rawResult="";
        switch (httpMethod) {
            case "GET":
                rawResult = WebClient.create("http://" + host + ":" + port + serviceName + endpoint)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .get(String.class);
        }
        this.result = rawResult;
    }

    @Then("^there is at least one result for getAll hotel$")
    public void at_least_one_result() {
        JSONArray hotels = new JSONArray(result);
        System.out.println("Lenght : "+hotels.length());
        assertTrue(hotels.length() > 0);
    }
}
