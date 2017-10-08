package carrent;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    private static ArrayList<Agency> agencies = new ArrayList<>();
    private static HashMap<String, Agency> agenciesByUID = new HashMap<>();
    private static HashMap<String, ArrayList<Agency>> agenciesByCity = new HashMap<>();
    private static HashMap<Integer, Car> CarsByUID = new HashMap<>();

    public static ArrayList<Agency> getAllAgencies() {
        return agencies;
    }

    public static ArrayList<Agency> getAgenciesByCity(String city) {
        return agenciesByCity.get(city);
    }

    public static Car getCarByUID(int UID) {
        return CarsByUID.get(UID);
    }

    static {
        try {

            JSONTokener tokener = new JSONTokener(new FileReader("/usr/local/tomee/data/Location-Agencies.json"));
            JSONArray rawArray = new JSONArray(tokener);

            for (int i = 0; i < rawArray.length(); i++) {
                JSONObject jsonAgency = rawArray.getJSONObject(i);

                CarType carType;

                try {
                    carType = CarType.valueOf(jsonAgency.getString("car_type"));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    continue;
                }

                // Create Agency Object
                int carUID = jsonAgency.getInt("UID");
                String agencyUID = jsonAgency.getString("agencyUID");
                String city = jsonAgency.getString("city");
                String agencyName = jsonAgency.getString("agency_name");
                double pricePerDay = jsonAgency.getDouble("price_per_day");
                boolean available = jsonAgency.getBoolean("available");

                Agency a = new Agency(agencyUID, agencyName, city);
                Car c = new Car(carUID, carType, pricePerDay, true, null, null);

                a.AddCar(c);

                // Add agency to global Collection
                agencies.add(a);

                // Add agency to agenciesByCity Map
                ArrayList<Agency> values = agenciesByCity.get(city);
                if (values == null) {
                    values = new ArrayList<Agency>();
                    agenciesByCity.put(city, values);
                }
                values.add(a);

                // Add the agency to the AgenciesByUID Map
                agenciesByUID.put(agencyUID, a);

                // Add the car to the carsByUID Map
                CarsByUID.put(carUID, c);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
