import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

public class Main {

    private String host = "localhost";
    private int port = 8080;

    public static void main(String[] args) {

        JSONObject flight = new JSONObject()
                .put("number", 479)
                .put("airline", "airfrance");

        JSONObject booking = new JSONObject()
                .put("type", "submit")
                .put("booking", new JSONObject()
                    .put("flight", flight));

        JSONObject answer = call(booking);
        System.out.println(answer.toString());
    }

    private static JSONObject call(JSONObject request) {
        System.out.println(request.toString());
        String raw =
                WebClient.create("http://" + "localhost" + ":" + 8080 + "/tta-booking/booking/")
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        return new JSONObject(raw);
    }
}
