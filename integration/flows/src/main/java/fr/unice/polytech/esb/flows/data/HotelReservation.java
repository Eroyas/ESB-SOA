package fr.unice.polytech.esb.flows.data;

import org.json.JSONObject;

import java.io.Serializable;

public class HotelReservation implements Serializable {
    private String name;
    private double price;
    private int room;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public JSONObject toJSONObject() {
        return new JSONObject()
                .put("name", this.name)
                .put("room", this.room)
                .put("price", this.price);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotelReservation that = (HotelReservation) o;

        if (price != that.price) return false;
        if (room != that.room) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + room;
        return result;
    }
}
