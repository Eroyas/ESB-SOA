package reservation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

@Path("/reservation")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationService {

    private static final int INDENT_FACTOR = 2;

    @GET
    public Response processGet()
    {
        String response = "Yololo";
        return Response.ok().entity(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(String input) {
        JSONObject obj = new JSONObject(input);
        try {
            switch ((obj.getString("type"))) {
                case "submission":
                    System.out.println("YOLOLO");
                    return Response.ok().entity("OK LOURD").build();
                /*
                case REGISTER:
                    return Response.ok().entity(register(obj).toString(INDENT_FACTOR)).build();
                case LIST:
                    return Response.ok().entity(list(obj).toString(INDENT_FACTOR)).build();
                case DUMP:
                    return Response.ok().entity(dump(obj).toString(INDENT_FACTOR)).build();
                case RETRIEVE:
                    return Response.ok().entity(retrieve(obj).toString(INDENT_FACTOR)).build();
                case PURGE:
                    return Response.ok().entity(purge(obj).toString(INDENT_FACTOR)).build();
                case DELETE:
                    return Response.ok().entity(delete(obj).toString(INDENT_FACTOR)).build();
                    */
            }
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString(INDENT_FACTOR)).build();
        }
        return null;
    }

}
