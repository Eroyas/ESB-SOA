package items;

import exceptions.EmptyBookingException;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Booking {

    private Status status;
    private Flight flight;
    private Hotel hotel;
    private Car car;

    @MongoObjectId
    String _id;

    public Booking(JSONObject booking) throws EmptyBookingException {
        this.status = Status.WAITING_APPROVAL;
        try {
            this.flight = new Flight(booking.getJSONObject("flight"));
        }
        catch (NullPointerException e){
            this.flight = null;
        }
        try {
            this.hotel = new Hotel(booking.optJSONObject("hotel"));
        }
        catch (NullPointerException e){
            this.hotel = null;
        }
        try {
            this.car = new Car(booking.optJSONObject("car"));
        }
        catch (NullPointerException e){
            this.car = null;
        }
        if (this.flight == null && this.hotel == null && this.car == null)
        {
            throw new EmptyBookingException();
        }
    }

    public JSONObject toJson()
    {
        JSONObject result = new JSONObject();

        result.put("status", this.status.getStr());

        if (this.flight != null)
                result.put("flight", this.flight.toJson());
        if (this.hotel != null)
                result.put("hotel", this.hotel.toJson());
        if (this.car != null)
                result.put("car", this.car.toJson());
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "status=" + status +
                ", flight=" + flight +
                ", hotel=" + hotel +
                ", car=" + car +
                ", _id='" + _id + '\'' +
                '}';
    }
}
