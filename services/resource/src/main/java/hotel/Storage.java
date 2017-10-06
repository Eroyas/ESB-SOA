package hotel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

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

                array.put(item);
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

                array.put(item);
            }
        }

        hotels.put("hotels", array);

        return hotels;
    }

    public static JSONObject readForReservation(String city, String name, String arrival, String departure) {
        JSONObject hotels = new JSONObject();

        JSONArray array = new JSONArray();

        for(Hotel hotel: contents.values()) {

            if(hotel.getCity().equalsIgnoreCase(city) && hotel.getName().equalsIgnoreCase(name)) {

                JSONObject item = new JSONObject();

                item.put("id", hotel.getId());
                item.put("hotel_name", hotel.getName());
                item.put("city", hotel.getCity());
                item.put("arrival_date", arrival);
                item.put("departure_date", departure);

                long nbNight = getNumberOfNight(arrival, departure);
                item.put("number_of_night", nbNight);

                item.put("hotel_type", hotel.getType());
                item.put("price_per_night", hotel.getAmount());

                array.put(item);
            }
        }

        hotels.put("hotels", array);

        return hotels;
    }

    private static long getNumberOfNight(String arrival, String departure) {
        try{
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

            // setLenient : cela permet de respecter le nombre de jours par mois
            format.setLenient(false);
            Date arr = format.parse(arrival);
            Date dep = format.parse(departure);

            // on vérif bien que la date de départ est bien après celle de l'arrivée
            if (dep.getTime() > arr.getTime()) {
                long diff = dep.getTime() - arr.getTime();
                return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            }

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void delete(Integer id) {
        contents.remove(id);
    }

    public static Collection<Hotel> findAll() {
        return contents.values();
    }

    static {
        try {

            JSONTokener tokener = new JSONTokener(new FileReader("/usr/local/tomee/data/Hotels_DB.json"));
            JSONObject jsonObject = new JSONObject(tokener);

            JSONArray hotels = (JSONArray) jsonObject.get("hotels");

            for (int id = 0; id < hotels.length(); id++) {
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
