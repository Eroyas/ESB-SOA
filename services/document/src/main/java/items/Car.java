package items;

import org.json.JSONObject;

public class Car {

    private String model;
    private int id;

    public Car(JSONObject car) {
        this.model = car.getString("model");
        this.id = car.getInt("id");
    }

    JSONObject toJson() {
        return new JSONObject()
                .put("model", model)
                .put("id", id);
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", id=" + id +
                '}';
    }
}
