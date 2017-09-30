package hotel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Eroyas on 25/09/17.
 */
public class Storage {

    private static HashMap<Integer, Hotel> contents = new HashMap<Integer, Hotel>();

    public static void create(Integer id, String name, String city, String type, double amount) {
        contents.put(id, new Hotel(id, name, city, type, amount));
    }

    public static Hotel readById(Integer id) {
        return contents.get(id);
    }

    public static JSONObject readByName(String name) {
        JSONObject hotels = new JSONObject();

        JSONArray array = new JSONArray();

        for(Hotel hotel: contents.values()) {

            if(hotel.getName().equalsIgnoreCase(name)) {
                JSONObject item = new JSONObject();

                item.put("id", hotel.getId());
                item.put("hotel_name", hotel.getName());
                item.put("city", hotel.getCity());
                item.put("hotel_type", hotel.getType());
                item.put("price_per_night", hotel.getAmount());

                array.add(item);
            }
        }

        hotels.put("hotels", array);

        return hotels;
    }

    public static JSONObject readByCity(String city) {
        JSONObject hotels = new JSONObject();

        JSONArray array = new JSONArray();

        for(Hotel hotel: contents.values()) {

            if(hotel.getCity().equalsIgnoreCase(city)) {

                JSONObject item = new JSONObject();

                item.put("id", hotel.getId());
                item.put("hotel_name", hotel.getName());
                item.put("city", hotel.getCity());
                item.put("hotel_type", hotel.getType());
                item.put("price_per_night", hotel.getAmount());

                array.add(item);
            }
        }

        hotels.put("hotels", array);

        return hotels;
    }

    public static void delete(Integer id) {
        contents.remove(id);
    }

    public static Collection<Hotel> findAll() {
        return contents.values();
    }

    static {
        JSONParser parser = new JSONParser();

        try {
            // Relative path ne marche pas..
            Object obj = parser.parse(new FileReader(
                    "/home/user/ESB-SOA/datasets/hotels/Hotels_DB.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray hotels = (JSONArray) jsonObject.get("hotels");

            for (int id = 0; id < hotels.size(); id++) {
                JSONObject hotel = (JSONObject) hotels.get(id);

                String name = (String) hotel.get("hotel_name");
                String city = (String) hotel.get("city");
                String type = (String) hotel.get("hotel_type");
                String amount = (String) hotel.get("price_per_night");

                Storage.create(id, name, city, type, Double.parseDouble(amount.replace(",",".")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // demo de base
        // Storage.create(1,"Ibis", "Paris", "Chambre luxe", 99.99);
        // Storage.create(2,"Ibis", "Gap", "Chambre luxe", 99.99);
        // Storage.create(3,"F1", "Paris", "Chambre simple", 33.33);
        // Storage.create(4,"F1", "Paris", "Chambre double", 53.33);
    }

}
