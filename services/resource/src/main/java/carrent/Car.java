package carrent;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.util.Date;

public class Car {
    private int UID;
    private CarType carType;
    private double pricePerDay;
    private boolean available;
    private DateTime rentStart;
    private DateTime rentEnd;

    public Car(int UID, CarType carType, double pricePerDay, boolean available, DateTime rentStart, DateTime rentEnd) {
        this.UID = UID;
        this.carType = carType;
        this.pricePerDay = pricePerDay;
        this.available = available;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
    }

    public void rent(DateTime startDate, DateTime endDate) {
        available=false;
        rentStart = startDate;
        rentEnd = endDate;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public JSONObject toJSONOject() {
        JSONObject thisObject = new JSONObject();
        thisObject.put("UID", UID);
        thisObject.put("carType", carType.toString());
        thisObject.put("pricePerDay", pricePerDay);
        thisObject.put("available", available);
        if (rentStart == null) {
            thisObject.put("rentStart", "N/A");
        } else {
            thisObject.put("rentStart", rentStart.toString());
        }
        if (rentEnd == null) {
            thisObject.put("rentEnd", "N/A");
        } else {
            thisObject.put("rentEnd", rentEnd.toString());
        }
        return thisObject;
    }
}
