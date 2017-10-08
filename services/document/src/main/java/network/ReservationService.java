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
    //TODO: est-ce-que ça c'est mauvais? si oui? est-ce VRAIMENT mauvais?
    public static MongoConnector mongoConnector = new MongoConnector();

    // Testing availability scope.
    @GET
    public Response availabilityChecking(){
        return Response.ok().entity("Service is up, that's confirmed!").build();
    }

    // Listening for JSON Document request, the switch operates over the key "type"
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(String input) {
        JSONObject obj = new JSONObject(input);
        System.out.println("Receving request with JSON nested object: " + obj.toString());
        try {
            JSONObject answer = null;
            switch ((obj.getString("type"))) {
                case "submit":
                    answer = handler.submitBooking(obj.getJSONObject("booking"));
                    break;
                case "validate":
                    answer = handler.approveBooking(obj.getInt("id"));
                    break;
                case "reject":
                    answer = handler.rejectBooking(obj.getInt("id"));
                    break;
                case "retrieve":
                    answer = handler.retrieveBooking(obj.getInt("id"));
                    break;
            }

            if (answer != null)
                return Response.ok().entity(answer.toString(INDENT_FACTOR)).build();

        }catch(Exception e) {
            JSONObject error = new JSONObject().put("Error, please mind reading the message: ", e.toString());
            System.err.println("Error while processing the POST request.");
            e.printStackTrace();
            return Response.status(400).entity(error.toString(INDENT_FACTOR)).build();
        }
        return null;
    }

}
