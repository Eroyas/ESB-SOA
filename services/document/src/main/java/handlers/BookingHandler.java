package handlers;

import exceptions.EmptyBookingException;
import items.bill.Payment;
import items.booking.Booking;
import network.BookingService;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookingHandler {

    public JSONObject submitBooking(JSONObject bookingAsJson)
    {
        try {
            MongoCollection bookings = BookingService.mongoConnector.getBookings();
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
            MongoCollection bookings = BookingService.mongoConnector.getBookings();
            bookings.update("{travelId:#}", idToValidate).upsert().multi().with("{$set: {'status': 'APPROVED'}}");

            return new JSONObject()
                    .put("travelId", idToValidate)
                    .put("approved", true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONObject()
                    .put("approved", false)
                    .put("travelId", idToValidate)
                    .put("message", "Error occured: " + e.getMessage());
        }
    }

    public JSONObject rejectBooking(int idToReject)
    {
        try {
            MongoCollection bookings = BookingService.mongoConnector.getBookings();
            bookings.update("{travelId:#}", idToReject).with("{$set: {'status': 'REJECTED'}}");

            return new JSONObject()
                    .put("rejected", true)
                    .put("travelId", idToReject);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONObject()
                    .put("rejected", false)
                    .put("travelId", idToReject)
                    .put("message", "Error occured: " + e.getMessage());
        }
    }

    public JSONObject retrieveBooking(int idToRetrieve)
    {
        try {
            MongoCollection bookings = BookingService.mongoConnector.getBookings();
            MongoCollection payments = BookingService.mongoConnector.getPayments();
            Booking booking = bookings.findOne("{travelId:#}", idToRetrieve).as(Booking.class);

            MongoCursor<Payment> cursor = payments.find("{travelId:#}", idToRetrieve).as(Payment.class);
            List<Payment> bookingPayments = new ArrayList<>();
            for(Payment payment : cursor)
                bookingPayments.add(payment);

            return new JSONObject()
                    .put("retrieved", true)
                    .put("booking", booking.toJson())
                    .put("payments", bookingPayments);
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
