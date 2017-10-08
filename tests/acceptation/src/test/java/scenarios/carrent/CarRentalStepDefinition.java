package scenarios.carrent;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dockerized.DockerizedTest;
import org.apache.cxf.jaxrs.client.WebClient;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.*;

public class CarRentalStepDefinition extends DockerizedTest{

    private String host = getDockerHost();
    private String serviceName = "/tta-car-and-hotel";
    private int port = 9080;

    private String endpoint;
    private String httpMethod;

    private int carUID;
    private String startDate;
    private String endDate;

    private String result;

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
        WebClient webClient = WebClient.create("http://" + host + ":" + port + serviceName + endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", MediaType.APPLICATION_JSON);

        switch (httpMethod) {
            case "GET":
                 rawResult = webClient.get(String.class);
        }
        this.result = rawResult;
    }

    @Then("^there is at least one result$")
    public void at_least_one_result() {
        JSONArray cities = new JSONArray(result);
        assertTrue(cities.length()>0);
    }

    @And("^the city is '(.*)'$")
    public void theCityIsCity(String cityName) {
        endpoint += "/" + cityName;
    }

    @Then("^there are (\\d+) result$")
    public void thereAreNb_agenciesResult(int nbAgency) throws Throwable {
        JSONArray agencies = new JSONArray(result);
        assertEquals(nbAgency, agencies.length());
    }

    @Given("^the carUID (\\d+)$")
    public void theCarUID(int carUID) {
        this.carUID = carUID;
    }

    @And("^the starting date is \"(.*)\"$")
    public void theStartingDateIs(String startDate) {
        this.startDate =startDate;
    }

    @And("^the ending date is \"([^\"]*)\"$")
    public void theEndingDateIs(String endDate) {
        this.endDate = endDate;
    }

    @And("^the request to rent a car is sent$")
    public void theRequestToRentACarIsSent() {
        JSONObject request = new JSONObject();
        request.put("UID", carUID);
        request.put("start_date", startDate);
        request.put("end_date", endDate);

        String rawResult="";
        WebClient webClient = WebClient.create("http://" + host + ":" + port + serviceName + endpoint+"/"+carUID)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", MediaType.APPLICATION_JSON);

        System.out.println("http://" + host + ":" + port + serviceName + endpoint);

        switch (httpMethod) {
            case "POST":
                rawResult = webClient.post(request.toString(),String.class);
        }
        this.result = rawResult;
    }

    @Then("^the status reponse is \"(.*)\"$")
    public void theStatusReponseIs(String status) {
        JSONObject jsonresult = new JSONObject(result);
        System.out.println(jsonresult);
        assertEquals("ok", jsonresult.getString("status"));
    }

    @And("^the total price is (.*)$")
    public void theTotalPriceIs(String doubleStringprice) {
        JSONObject jsonresult = new JSONObject(result);
        double thePrice = Double.parseDouble(doubleStringprice);
        assertEquals(thePrice, jsonresult.getDouble("total_price"), 0);
    }
}
