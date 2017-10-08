package handler;

import exceptions.EmptyBookingException;
import items.Booking;
import network.ReservationService;
import org.jongo.MongoCollection;
import org.json.JSONObject;

public class Handler {

    //TODO: contraindre la db pour éviter doublons, pas réellement scope
    public JSONObject submitBooking(JSONObject bookingAsJson)
    {
        try {
            MongoCollection bookings = ReservationService.mongoConnector.getBookings();
            Booking booking = new Booking(bookingAsJson);
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
            MongoCollection bookings = ReservationService.mongoConnector.getBookings();
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
            MongoCollection bookings = ReservationService.mongoConnector.getBookings();
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

    public JSONObject retrieveBooking(int idToRetrieve)
    {
        try {
            MongoCollection bookings = ReservationService.mongoConnector.getBookings();
            Booking booking = bookings.findOne("{id:#}", idToRetrieve).as(Booking.class);

            return booking.toJson().put("retrieved", true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONObject()
                    .put("retrieved", false)
                    .put("id", idToRetrieve)
                    .put("message", "Error occured: " + e.getMessage());
        }
    }

}
