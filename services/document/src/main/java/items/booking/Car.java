package items.booking;

import org.json.JSONObject;

public class Car {

    private String model;
    private String brand;
    private double pricePerDay;

    public Car(){}

    public Car(JSONObject car) {
        this.model = car.getString("model");
        this.brand = car.getString("brand");
        //TODO: chiant qu'on ait des int et des double
        this.pricePerDay = (double) car.getInt("rentPricePerDay");
    }

    JSONObject toJson() {
        return new JSONObject()
                .put("model", model)
                .put("brand", brand);
    }


    //TODO: define behavior with the price vs priceperday etc
    public double getPrice() {
        return pricePerDay;
    }
}
