package fr.unice.polytech.esb.flows.data;

import java.io.Serializable;

public class HotelReservation implements Serializable{
    private String hotelName;
    private int price;
    private int roomNumber;

    public String getHotelName() {

        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotelReservation that = (HotelReservation) o;

        if (price != that.price) return false;
        if (roomNumber != that.roomNumber) return false;
        return hotelName.equals(that.hotelName);
    }

    @Override
    public int hashCode() {
        int result = hotelName.hashCode();
        result = 31 * result + price;
        result = 31 * result + roomNumber;
        return result;
    }
}
