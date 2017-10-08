package bookingEngine;

import dockerized.DockerizedTest;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Assets extends DockerizedTest{

    public static JSONObject call(JSONObject request) {
        System.out.println("Sending request: " + request.toString());
        String raw =
                WebClient.create("http://" + getDockerHost() + ":" + 8080 + "/tta-booking/booking")
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        System.out.println("Received: " + raw);
        return new JSONObject(raw);
    }

    public static Response get(){
        return WebClient.create("http://" + getDockerHost() + ":" + 8080 +"/tta-booking/booking")
                .get();
    }
}
