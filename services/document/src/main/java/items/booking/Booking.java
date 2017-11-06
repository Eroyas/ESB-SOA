package items.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.CustomerNotFoundException;
import exceptions.EmptyBookingException;
import items.assets.Customer;
import org.json.JSONException;
import org.json.JSONObject;

public class Booking {

    private int travelId;
    private Status status;
    @JsonProperty("flight")
    private Flight flight;
    @JsonProperty("hotel")
    private Hotel hotel;
    @JsonProperty("car")
    private Car car;
    @JsonProperty("customer")
    private Customer customer;

    public Booking(){}

    public Booking(JSONObject booking) throws EmptyBookingException, CustomerNotFoundException {
        this.status = Status.WAITING_APPROVAL;
        try {
            this.flight = new Flight(booking.getJSONObject("flight"));
        }
        catch (JSONException e){
            this.flight = null;
        }
        try {
            this.hotel = new Hotel(booking.getJSONObject("hotel"));
        }
        catch (JSONException e){
            this.hotel = null;
        }
        try {
            this.car = new Car(booking.getJSONObject("car"));
        }
        catch (JSONException e){
            this.car = null;
        }
        this.travelId = booking.getInt("travelId");
        if (this.flight == null && this.hotel == null && this.car == null)
        {
            throw new EmptyBookingException();
        }
        try{
            this.customer = new Customer(booking.getJSONObject("customer"));
        }
        catch(JSONException e){
            throw new CustomerNotFoundException();
        }
    }

    public JSONObject toJson()
    {
        JSONObject result = new JSONObject();

        result.put("status", this.status.getStr());
        result.put("travelId", this.travelId);
        result.put("customer", this.customer.toJson());

        if (this.flight != null)
                result.put("flight", this.flight.toJson());
        if (this.hotel != null)
                result.put("hotel", this.hotel.toJson());
        if (this.car != null)
                result.put("car", this.car.toJson());

        return result;
    }

    public int getTravelId() {
        return travelId;
    }

    public Status getStatus() {
        return status;
    }

    public Flight getFlight() {
        return flight;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (travelId != booking.travelId) return false;
        if (status != booking.status) return false;
        if (flight != null ? !flight.equals(booking.flight) : booking.flight != null) return false;
        if (hotel != null ? !hotel.equals(booking.hotel) : booking.hotel != null) return false;
        if (car != null ? !car.equals(booking.car) : booking.car != null) return false;
        return customer.equals(booking.customer);
    }

    @Override
    public int hashCode() {
        int result = travelId;
        result = 31 * result + status.hashCode();
        result = 31 * result + (flight != null ? flight.hashCode() : 0);
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + customer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "travelId=" + travelId +
                ", status=" + status +
                ", flight=" + flight +
                ", hotel=" + hotel +
                ", car=" + car +
                ", customer=" + customer +
                '}';
    }

}
