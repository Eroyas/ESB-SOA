package carrent;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rent")
@Produces(MediaType.APPLICATION_JSON)
public class CarRentalService {

    /**
     * List all cities where there is at least 1 agency
     * @return all list of all cities containing at least one agency
     */
    @GET
    public Response getAllCities() {
        return null; //IT works
    }
}
