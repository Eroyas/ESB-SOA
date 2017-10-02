package bookingEngine;

import dockerized.DockerizedTest;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.core.MediaType;

public class BookingActionsTest extends DockerizedTest{

    @Test
    public void submitBookingTest() {
        JSONObject flight = new JSONObject()
                .put("number", (int) (Math.random() * 1000))
                .put("airline", "airfrance");

        JSONObject booking = new JSONObject()
                .put("type", "submit")
                .put("booking", new JSONObject()
                        .put("id", (int) (Math.random() * 10000))
                        .put("flight", flight));

        JSONObject answer = call(booking, "booking");

        JSONAssert.assertEquals(booking.getJSONObject("booking"), answer.getJSONObject("booking"), false);
        Assert.assertTrue(answer.getJSONObject("booking").optString("status").equals("WAITING_APPROVAL"));
        Assert.assertTrue(answer.getBoolean("inserted"));
    }

    @Test
    public void validateBookingTest() {
        JSONObject request = new JSONObject()
                .put("type", "validate")
                .put("id", 1);

        JSONObject flight = new JSONObject()
                .put("number", 123)
                .put("airline", "airfrance");

        JSONObject booking = new JSONObject()
                .put("id", 1)
                .put("flight", flight);

        JSONObject answer = call(request, "booking");

        System.out.println(answer.toString());
        //JSONAssert.assertEquals(booking, answer.getJSONObject("booking"), false);
        Assert.assertTrue(answer.getBoolean("approved"));
    }

    /* ASSETS */

    public static JSONObject call(JSONObject request, String param) {
        String raw =
                WebClient.create("http://" + getDockerHost() + ":" + 8080 + "/tta-booking/" + param)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        return new JSONObject(raw);
    }
}
