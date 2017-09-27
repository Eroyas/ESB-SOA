package carrent;

import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Path("/rent")
@Produces(MediaType.APPLICATION_JSON)
public class CarRentalService {

    /**
     * List all cities where there is at least 1 agency
     * @return all list of all cities containing at least one agency
     */
    @GET
    public Response getAllCities() {
        ArrayList<Agency> agencies = Storage.getAllAgencies();
        Set<String> cities = new HashSet<>();
        for (Agency a : agencies) {
            String city = a.getCity();
            if (!cities.contains(city)) {
                cities.add(city);
            }
        }

        JSONArray result = new JSONArray(cities);
        return Response.ok().entity(result).build();
    }
}
