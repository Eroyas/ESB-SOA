package handler;

import com.mongodb.Mongo;
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
            System.out.println("Booking before insert: " + booking.toString());
            bookings.insert(booking);
            System.out.println("Booking inserted.");

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

    public boolean validateBooking(JSONObject bookingAsJson)
    {
        try {
            MongoCollection bookings = MongoConnector.getBookings();
            Booking booking = new Booking(bookingAsJson);
            bookings.update("{'airline':#");

            return true;
        }
        catch (Exception e)
        {
            System.err.println(e.getStackTrace());
            return false;
        }
    }


    public String responseForger(Map<String, String> map)
    {
        return "toz";
    }
}
