package hotel;

import org.json.JSONArray;
import org.json.JSONString;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Date;

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

    // http://localhost:8080/tta-car-and-hotel/hotels/Paris/Ibis/01-10-2017/03-10-2017
    @Path("{city}/{name}/{arrival}/{departure}")
    @GET
    public Response getHotelReservation(@PathParam("city") String city,
                                        @PathParam("name") String name,
                                        @PathParam("arrival") String arrival,
                                        @PathParam("departure") String departure) {

        if(Storage.readForReservation(city, name, arrival, departure) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(Storage.readForReservation(city, name, arrival, departure).toString()).build();
    }

}
