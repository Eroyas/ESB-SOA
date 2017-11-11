package fr.unice.polytech.esb.flows.data;

import java.io.Serializable;

public class HotelReservation implements Serializable{
    private String name;
    private int price;
    private int room;


    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
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
        int result = name.hashCode();
        result = 31 * result + price;
        result = 31 * result + room;
        return result;
    }

    @Override
    public String toString() {
        return  "{\"booking\": " +
                    "{\"hotel\": {" +
                        "\"name\": \"" + name + "\"" +
                        ", \"price\": " + price +
                        ", \"room\": " + room +
                        "}" +
                    "}" +
                "}";
    }
}
