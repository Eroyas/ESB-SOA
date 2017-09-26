package hotel;

import org.json.JSONArray;
import org.json.JSONString;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by Eroyas on 25/09/17.
 */

@Path("/hotels")
@Produces(MediaType.APPLICATION_JSON)
public class HotelService {

    // http://localhost:8080/tcs-service-rest/hotels
    @GET
    public Response getHotels() {
        Collection<Hotel> hotels = Storage.findAll();
        JSONArray result = new JSONArray();

        for(Hotel hotel: hotels) {
            result.put(hotel.getName());
        }

        return Response.ok().entity(result.toString(2)).build();
    }


    // http://localhost:8080/tcs-service-rest/hotels/Ibis
    @Path("/{name}")
    @GET
    public Response getHotelsByName(@PathParam("name") String name) {
        if(Storage.read(name) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity("\"" + Storage.read(name).toString() + "\"").build();
    }

}
