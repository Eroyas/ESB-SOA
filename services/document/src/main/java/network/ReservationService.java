package network;

import handler.Handler;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        System.out.println("Receving request with JSON nested object: " + obj.toString());
        try {
            JSONObject answer;
            switch ((obj.getString("type"))) {
                case "submit":
                    answer = handler.submitBooking(obj.getJSONObject("booking"));
                    return Response.ok().entity(answer.toString(INDENT_FACTOR)).build();
                case "validate":
                    answer = handler.approveBooking(obj.getInt("id"));
                    return Response.ok().entity(answer.toString(INDENT_FACTOR)).build();
            }
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            e.printStackTrace();
            return Response.status(400).entity(error.toString(INDENT_FACTOR)).build();
        }
        return null;
    }

}
