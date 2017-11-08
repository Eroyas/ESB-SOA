package items.booking;

import org.json.JSONObject;

public class Hotel {

    private String name;
    private int room;
    private double price;

    public Hotel() {}

    public Hotel(JSONObject data) {
        System.out.println("Hotel: " + data);
        this.name = data.getString("name");
        this.room = data.getInt("room");
        this.price = data.getDouble("price");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("name", name)
                .put("room", room)
                .put("price", price);
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        if (room != hotel.room) return false;
        if (Double.compare(hotel.price, price) != 0) return false;
        return name.equals(hotel.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + room;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", room=" + room +
                ", price=" + price +
                '}';
    }

}

