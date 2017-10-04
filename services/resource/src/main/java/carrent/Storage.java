package carrent;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Storage {
    private static ArrayList<Agency> agencies = new ArrayList<>();

    public static ArrayList<Agency> getAllAgencies() {
        return agencies;
    }

    static {
        try {

            JSONTokener tokener = new JSONTokener(new FileReader("/usr/local/tomee/data/Location-Agencies.json"));
            JSONArray rawArray = new JSONArray(tokener);

            for (int i = 0; i < rawArray.length(); i++) {
                JSONObject jsonAgency = rawArray.getJSONObject(i);
                agencies.add(new Agency(
                        jsonAgency.getString("agency_name"),
                        jsonAgency.getString("city"),
                        jsonAgency.getString("car_type"),
                        jsonAgency.getDouble("price_per_day")));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
