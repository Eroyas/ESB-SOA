package carrent;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Future;

public class Storage {
    private static ArrayList<Agency> agencies = new ArrayList<>();
    private static HashMap<String, ArrayList<Agency>> agenciesByCity = new HashMap<>();

    public static ArrayList<Agency> getAllAgencies() {
        return agencies;
    }

    public static ArrayList<Agency> getAgenciesByCity(String city) {
        return agenciesByCity.get(city);
    }

    static {
        try {

            System.out.println("Starting Database...");

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

                String city = jsonAgency.getString("city");
                Agency a = new Agency(
                        jsonAgency.getString("agency_name"),
                        city,
                        carType,
                        jsonAgency.getDouble("price_per_day"));

                agencies.add(a);

                ArrayList<Agency> values = agenciesByCity.get(city);
                if (values == null) {
                    values = new ArrayList<Agency>();
                    agenciesByCity.put(city, values);
                }
                values.add(a);
            }

            System.out.println("Database is ready");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
