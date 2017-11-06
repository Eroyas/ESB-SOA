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
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("name", name)
                .put("room", room);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", room=" + room +
                '}';
    }

    public double getPrice() {
        return price;
    }
}

