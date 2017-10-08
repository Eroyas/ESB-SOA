package scenarios.bookingEngine;

import bookingEngine.Assets;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import dockerized.DockerizedTest;
import org.json.JSONObject;
import org.junit.Assert;


public class BookingRetrieval extends DockerizedTest {

    private JSONObject booking;

    @Then("^there is one booking retrievable with id (\\d+)$")
    public void retrieveBooking(int id){
        JSONObject request = new JSONObject()
                .put("type", "retrieve")
                .put("id", id);
        JSONObject booking = Assets.call(request);
        Assert.assertTrue(booking.getInt("id") == id);
        this.booking = booking;
    }

    @Given("^the (.*) is (.*)$")
    public void checkBookingAttributes(String key, String value){
        switch (key){
            case "status":
                Assert.assertTrue(booking.getString("status").equals(value));
                break;
            case "identity":
                String[] identityTab = value.split(" ");
                JSONObject identity = booking.getJSONObject("identity");
                Assert.assertTrue(identity.getString("firstName").equals(identityTab[0]));
                Assert.assertTrue(identity.getString("lastName").equals(identityTab[1]));
                Assert.assertTrue(identity.getString("email").equals(identityTab[2]));
                break;
            case "flight":
                String[] flightTab = value.split(" ");
                JSONObject flight = booking.getJSONObject("flight");
                Assert.assertTrue(flight.getString("airline").equals(flightTab[0]));
                Assert.assertTrue(flight.getInt("number") == Integer.parseInt(flightTab[1]));
                break;
            case "car":
                String[] carTab = value.split(" ");
                JSONObject car = booking.getJSONObject("car");
                Assert.assertTrue(car.getString("model").equals(carTab[0]));
                Assert.assertTrue(car.getInt("id") == Integer.parseInt(carTab[1]));
                break;
            case "hotel":
                String[] hotelTab = value.split(" ");
                JSONObject hotel = booking.getJSONObject("hotel");
                Assert.assertTrue(hotel.getString("name").equals(hotelTab[0]));
                Assert.assertTrue(hotel.getInt("room") == Integer.parseInt(hotelTab[1]));
                break;
        }
    }

}
