package hotel;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Eroyas on 25/09/17.
 */
public class Storage {

    private static HashMap<String, Hotel> contents = new HashMap<String, Hotel>();

    public static void create(String name, String city, String type, double amount) {
        contents.put(name, new Hotel(name, city, type, amount));
    }

    public static Hotel read(String name) {
        return contents.get(name);
    }

    public static void delete(String name) {
        contents.remove(name);
    }

    public static Collection<Hotel> findAll() {
        return contents.values();
    }

    static {
        // demo
        Storage.create("Ibis", "Paris", "Chambre luxe", 99.99);
        Storage.create("F1", "Paris", "Chambre simple", 33.33);
    }

}
