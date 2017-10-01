package scenarios.carrent;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.*;

public class CarRentalStepDefinition {

    private String host = "192.168.99.100";
    private String serviceName = "/tta-car-and-hotel";
    private int port = 9080;

    private String endpoint;
    private String httpMethod;

    private String result;

    @Given("^the car rent service is up$")
    public void the_car_rent_service_is_up() {

    }

    @When("^the endpoint is '(.*)'$")
    public void set_endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("^the select method is (.*)$")
    public void set_httpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    @When("^the request is sent$")
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

    @Then("^there is at least one result$")
    public void at_least_one_result() {
        JSONArray cities = new JSONArray(result);
        assertTrue(cities.length()>0);
    }
}
