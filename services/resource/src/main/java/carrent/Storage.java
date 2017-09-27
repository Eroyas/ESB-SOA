package carrent;


import java.util.ArrayList;

public class Storage {
    private static ArrayList<Agency> agencies = new ArrayList<>();

    public static ArrayList<Agency> getAllAgencies() {
        return agencies;
    }

    static {
        agencies.add(new Agency("Europcart", "Nice", "Estate", 40));
        agencies.add(new Agency("Hertz", "Nice", "City", 30));
    }
}
