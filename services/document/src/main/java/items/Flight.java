package items;

import org.json.JSONObject;

public class Flight {

    private String airline;
    private int number;

    public Flight() {}

    public Flight(JSONObject data) {
        System.out.println("Flight: " + data);
        this.airline = data.getString("airline");
        this.number = data.getInt("number");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("airline", airline)
                .put("number", number);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "airline='" + airline + '\'' +
                ", number=" + number +
                '}';
    }

}
