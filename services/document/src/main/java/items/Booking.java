package items;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.EmptyBookingException;
import items.assets.Status;
import items.assets.date.DateInterval;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.util.List;

public class Booking {

    private Status status;
    @JsonProperty("flight")
    private Flight flight;
    private Hotel hotel;
    private Car car;
    private int id;
    private Identity identity;
    private List<Spending> spendings;
    private DateInterval dateInterval;

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
        this.identity = new Identity(booking.getJSONObject("identity"));
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
        result.put("identity", this.identity.toJson());

        if (this.flight != null)
                result.put("flight", this.flight.toJson());
        if (this.hotel != null)
                result.put("hotel", this.hotel.toJson());
        if (this.car != null)
                result.put("car", this.car.toJson());
        return result;
    }

    public double getTotalPrice(){
        double price = 0;
        price += this.car.getPrice();
        price += this.flight.getPrice();
        price += this.hotel.getPrice();

        return price;
    }

    public double getTotalSpendings(){
        double price = 0;
        for (Spending spending : this.spendings){
            price += spending.getPrice();
        }

        return price;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "status=" + status +
                ", flight=" + flight +
                ", hotel=" + hotel +
                ", car=" + car +
                ", id=" + id +
                ", identity=" + identity +
                ", _id='" + _id + '\'' +
                '}';
    }

    //TODO: regenerate
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
        if (identity != null ? !identity.equals(booking.identity) : booking.identity != null) return false;
        return _id != null ? _id.equals(booking._id) : booking._id == null;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (flight != null ? flight.hashCode() : 0);
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (identity != null ? identity.hashCode() : 0);
        result = 31 * result + (_id != null ? _id.hashCode() : 0);
        return result;
    }

}
