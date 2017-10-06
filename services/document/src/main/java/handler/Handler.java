package handler;

import exceptions.EmptyBookingException;
import items.Booking;
import network.MongoConnector;
import org.jongo.MongoCollection;
import org.json.JSONObject;

import java.util.Map;

public class Handler {

    //TODO: useless yet, don't know if it makes sense to load the collection at instanciation? does it?
    private MongoCollection bookings;

    //TODO: contraindre la db pour éviter doublons
    public JSONObject submitBooking(JSONObject bookingAsJson)
    {
        try {
            MongoCollection bookings = MongoConnector.getBookings();
            Booking booking = new Booking(bookingAsJson);
            //TODO: consistency booking ou booking nesté dans JSONObject
            bookings.insert(booking);

            return new JSONObject()
                    .put("inserted", true)
                    .put("booking", booking.toJson());
        }
        catch (EmptyBookingException e)
        {
            System.err.println(e.getMessage());
            return new JSONObject()
                    .put("inserted", false)
                    .put("message", e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONObject()
                    .put("inserted", false)
                    .put("message", "Unknown error occured: " + e.getMessage());
        }
    }

    public JSONObject approveBooking(int idToValidate)
    {
        try {
            MongoCollection bookings = MongoConnector.getBookings();
            bookings.update("{id:#}", idToValidate).upsert().multi().with("{$set: {'status': 'APPROVED'}}");

            return new JSONObject()
                    .put("id", idToValidate)
                    .put("approved", true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONObject()
                    .put("approved", false)
                    .put("id", idToValidate)
                    .put("message", "Error occured: " + e.getMessage());
        }
    }

    public JSONObject rejectBooking(int idToReject)
    {
        try {
            MongoCollection bookings = MongoConnector.getBookings();
            bookings.update("{id:#}", idToReject).with("{$set: {'status': 'REJECTED'}}");

            return new JSONObject()
                    .put("rejected", true)
                    .put("id", idToReject);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONObject()
                    .put("rejected", false)
                    .put("id", idToReject)
                    .put("message", "Error occured: " + e.getMessage());
        }
    }

}
