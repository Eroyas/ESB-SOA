package handlers;

import exceptions.CustomerNotFoundException;
import exceptions.TravelNotFoundException;
import items.bill.Payment;
import items.booking.Booking;
import network.BillService;
import network.BookingService;
import org.jongo.MongoCollection;
import org.json.JSONObject;

public class BillHandler {

    public JSONObject submitBill(JSONObject paymentAsJson){
        try {
            MongoCollection payments = BillService.mongoConnector.getPayments();
            MongoCollection bookings = BookingService.mongoConnector.getBookings();
            Payment payment = new Payment(paymentAsJson);
            Booking booking = bookings.findOne("{travelId:#}", payment.getTravelId()).as(Booking.class);
            //Finding the Travel linked with the bill Id
            if (booking == null)
                throw new TravelNotFoundException();
            //Verifying that the customer corresponds
            else if (booking.getCustomer().getCustomerId() != payment.getCustomerId())
                throw new CustomerNotFoundException();
            else
                payments.insert(payment);

            return new JSONObject()
                    .put("inserted", true)
                    .put("payment", payment);
        }
        catch(CustomerNotFoundException | TravelNotFoundException e){
            System.err.println(e.getMessage());
            return new JSONObject()
                    .put("inserted", false)
                    .put("message", e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            return new JSONObject()
                    .put("inserted", false)
                    .put("message", "Unknown error occured: " + e.getMessage());
        }

    }

}
