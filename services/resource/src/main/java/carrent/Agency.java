package carrent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Maxime
 */
public class Agency {
    private String UID;
    private String name;
    private String city;
    private ArrayList<Car> cars;

    public Agency(String UID, String name, String city) {
        this.UID = UID;
        this.name = name;
        this.city = city;
        this.cars = new ArrayList<>();
    }

    public void AddCar(Car car) {
        cars.add(car);
    }

    public String getCity() {
        return city;
    }

    public JSONObject toJSONOject() {
        JSONObject thisObject = new JSONObject();

        thisObject.put("UID", UID);
        thisObject.put("name", name);
        thisObject.put("city", city);

        return thisObject;
    }
}
