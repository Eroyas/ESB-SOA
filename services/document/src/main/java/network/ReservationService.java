package network;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.util.JSONPObject;
import handler.Handler;
import org.json.JSONObject;

@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationService {

    private static final int INDENT_FACTOR = 2;
    private Handler handler = new Handler();

    // This get serves only as server availability testing
    @GET
    public Response process(){
        return Response.ok().entity("GET succesfully processed").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(String input) {
        JSONObject obj = new JSONObject(input);
        System.out.println("Receving req with JSON nested object: " + obj.toString());
        try {
            switch ((obj.getString("type"))) {
                case "submit":
                    System.out.println(obj.getJSONObject("booking"));
                    JSONObject answer = handler.submitBooking(obj.getJSONObject("booking"));
                    return Response.ok().entity(answer.toString(INDENT_FACTOR)).build();
                case "validate":
                    return Response.ok().entity("OK LOURD VALID").build();
            }
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            e.printStackTrace();
            return Response.status(400).entity(error.toString(INDENT_FACTOR)).build();
        }
        return null;
    }

}
