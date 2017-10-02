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

    // http://localhost:8080/tta-car-and-hotel/hotels
    @GET
    public Response getHotels() {
        Collection<Hotel> hotels = Storage.findAll();
        JSONArray result = new JSONArray();

        for(Hotel hotel: hotels) {
            result.put(hotel.getName());
        }

        return Response.ok().entity(result.toString(2)).build();
    }

    // http://localhost:8080/tta-car-and-hotel/hotels/name/Ibis
    @Path("name/{name}")
    @GET
    public Response getHotelsByName(@PathParam("name") String name) {
        if(Storage.readByName(name) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(Storage.readByName(name).toString()).build();
    }

    // http://localhost:8080/tta-car-and-hotel/hotels/city/Paris
    @Path("city/{city}")
    @GET
    public Response getHotelsByCity(@PathParam("city") String city) {
        if(Storage.readByCity(city) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(Storage.readByCity(city).toString()).build();
    }

}
