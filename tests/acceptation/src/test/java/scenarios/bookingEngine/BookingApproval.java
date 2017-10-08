package scenarios.bookingEngine;

import bookingEngine.Assets;
import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import dockerized.DockerizedTest;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;
import org.junit.Assert;

import javax.ws.rs.core.MediaType;

public class BookingApproval extends DockerizedTest {

    private JSONObject request;
    private JSONObject booking;


    @Given("^Service available at localhost:8080/tta-booking/booking$")
    public void testAvailability(){
        Assert.assertTrue(Assets.get().getStatus() == 200);
    }


    @Given("^A request with type set to submit$")
    public void createBookingRequest(){
        this.request = new JSONObject()
                .put("type", "submit");
        this.booking = new JSONObject();
    }

    @Given("^with the booking (.*) set to (.*)$")
    public void fillBooking(String key, String param){
        switch (key){
            case "id":
                this.booking.put("id", Integer.parseInt(param));
                break;
            case "identity":
                String[] identityTab = param.split(" ");
                JSONObject identity = new JSONObject()
                        .put("firstName", identityTab[0])
                        .put("lastName", identityTab[1])
                        .put("email", identityTab[2]);
                this.booking.put("identity", identity);
                break;
            case "flight":
                String[] flightTab = param.split(" ");
                JSONObject flight = new JSONObject()
                        .put("airline", flightTab[0])
                        .put("number", Integer.parseInt(flightTab[1]));
                this.booking.put("flight", flight);
                break;
            case "car":
                String[] carTab = param.split(" ");
                JSONObject car = new JSONObject()
                        .put("model", carTab[0])
                        .put("id", Integer.parseInt(carTab[1]));
                this.booking.put("car", car);
                break;
            case "hotel":
                String[] hotelTab = param.split(" ");
                JSONObject hotel = new JSONObject()
                        .put("name", hotelTab[0])
                        .put("room", Integer.parseInt(hotelTab[1]));
                this.booking.put("hotel", hotel);
                break;
        }
    }

    @When("^the request for booking sent")
    public JSONObject post() {
        this.request.put("booking", this.booking);
        String raw =
                WebClient.create("http://" + getDockerHost() + ":" + 8080 + "/tta-booking/booking")
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        return new JSONObject(raw);
    }



}
