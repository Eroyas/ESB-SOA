package items;

import exceptions.EmptyBookingException;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Booking {

    private Status status;
    private Flight flight;
    private Hotel hotel;
    private Car car;
    private int id;

    @MongoObjectId
    String _id;

    public Booking(){}

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
        this.id = booking.getInt("id");
        if (this.flight == null && this.hotel == null && this.car == null)
        {
            throw new EmptyBookingException();
        }
    }

    public JSONObject toJson()
    {
        JSONObject result = new JSONObject();

        result.put("status", this.status.getStr());
        result.put("id", this.id);

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
                ", id=" + id +
                ", _id='" + _id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (id != booking.id) return false;
        if (status != booking.status) return false;
        if (flight != null ? !flight.equals(booking.flight) : booking.flight != null) return false;
        if (hotel != null ? !hotel.equals(booking.hotel) : booking.hotel != null) return false;
        if (car != null ? !car.equals(booking.car) : booking.car != null) return false;
        return _id != null ? _id.equals(booking._id) : booking._id == null;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (flight != null ? flight.hashCode() : 0);
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (_id != null ? _id.hashCode() : 0);
        return result;
    }
}
