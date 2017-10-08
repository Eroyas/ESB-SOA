package carrent;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Path("/car-rental")
@Produces(MediaType.APPLICATION_JSON)
public class CarRentalService {

    /**
     * List all cities where there is at least 1 agency
     * @return all list of all cities containing at least one agency
     */
    @Path("/cities")
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

    /**
     * List all car that exist for a given city
     * @param cityName the name of the city for which to lookup car regardless of availability
     * @return A Collection of all the car offer for a the given city. Or 404 if the city does not exist
     */
    @Path("/search/{city}")
    @GET
    public Response listAgencyByCity(@PathParam("city") String cityName) {
        if (Storage.getAgenciesByCity(cityName)==null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        JSONArray jsonAgencies = new JSONArray();
        ArrayList<Agency> agencies = Storage.getAgenciesByCity(cityName);
        for (Agency a : agencies) {
            jsonAgencies.put(a.toJSONOject());
        }

        return Response.ok(jsonAgencies).build();
    }


    /**
     * Display Metadata about the car identified by its UID
     * @param carUID the car Unique Identifier
     * @return Metadata about the car such has its agency, the city, the price
     */
    @Path("/car/{carUID}")
    @GET
    public Response getAgency(@PathParam("carUID") String carUID) {
        int UID;
        try {
            UID = Integer.parseInt(carUID);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (Storage.getCarByUID(UID) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        JSONObject jsonCar = Storage.getCarByUID(UID).toJSONOject();
        return Response.ok(jsonCar).build();
    }

    /**
     * Tries to rent the car designed by its UID
     * @param payload the JSON Object containing the following rent metadata :
     *      {
     *        "UID": [the car UID]:Integer
     *        "start_date": [the location start date]:String in ISO-8601 format
     *        "end_date": [the location end date]:String in ISO-8601 format
     *      }
     *
     * @return a JSON Object like this :
     *      {
     *         "status": [ok|error]:String
     *         "message": [if an error happened, an error message explaining what went wrong]:String
     *         "total_price":[total price in euros if no error happened]:double
     *      }
     */
    @Path("/rentacar/{carUID}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rentCar(String payload) {
        JSONObject request = new JSONObject(payload);

        try {
            int carUID = request.getInt("UID");
            String rentStartString = request.getString("start_date");
            String rentEndString = request.getString("end_date");
            DateTime rentStart = new DateTime(rentStartString);
            DateTime rentEnd = new DateTime(rentEndString);

            Car carToRent = Storage.getCarByUID(carUID);
            if (carToRent == null) {
                JSONObject error = new JSONObject();
                error.put("status", "error");
                error.put("message", "no car found with this UID");
                return Response.status(400).entity(error).build();
            }

            if (!carToRent.isAvailable()) {
                JSONObject error = new JSONObject();
                error.put("status", "error");
                error.put("message", "car is not available");
                return Response.status(400).entity(error).build();
            }

            carToRent.rent(rentStart, rentEnd);
            int numberOfDaysToRent = Days.daysBetween(rentStart, rentEnd).getDays();
            double totalPrice = numberOfDaysToRent * carToRent.getPricePerDay();

            JSONObject success = new JSONObject();
            success.put("status", "ok");
            success.put("total_price", totalPrice);
            return Response.ok().entity(success).build();


        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("status", "error");
            error.put("message", e.getMessage());
            return Response.status(400).entity(error).build();
        }


    }
}
