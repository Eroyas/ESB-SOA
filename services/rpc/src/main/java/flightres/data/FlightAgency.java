package flightres.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class FlightAgency {
    private static HashMap<String, List<Flight>> agency = new HashMap<>();

    public static List<Flight> getAllFlightsFrom(String origin) {
        return agency.get(origin);
    }

    public  static List<Flight> getAllFlightsBetween(String origin, String arrival, String date)
    {
        if(!agency.containsKey(origin))
            return new ArrayList<>();

        List<Flight> listOfFlights = agency.get(origin);
        int i = 0;

        while(i < listOfFlights.size())
        {
            if(!(listOfFlights.get(i).getEndingAirport().equals(arrival) &&
                    listOfFlights.get(i).getStartDate().startsWith(date))) {
                listOfFlights.remove(i);
                i--;
            }
            i++;
        }

        return listOfFlights;
    }

    public static Flight getFlight(String start, String end, String date)
    {
        if(!agency.containsKey(start))
            return null;

        List<Flight> listOfFlights = agency.get(start);
        for(Flight flight: listOfFlights)
        {
            if(flight.getEndingAirport().equals(end) &&
                    flight.getStartDate().startsWith(date))
                return flight;
        }
        return null;
    }

    static
    {
        try
        {
            ClassLoader classLoader = FlightAgency.class.getClassLoader();
            JSONTokener flightsDataTokener = new JSONTokener(new FileReader(classLoader.getResource("flights/flights-data.json").getFile()));
            JSONArray flightsDB = new JSONArray(flightsDataTokener);

            Iterator iterator = flightsDB.iterator();
            while (iterator.hasNext())
            {
                JSONObject jsonFlight = (JSONObject) iterator.next();
                String startingAirport = (String) jsonFlight.get("airport_origin");
                String endingAirport = (String) jsonFlight.get("airport_destination");
                String startingDate = (String) jsonFlight.get("date_departure");
                String endingDate = (String) jsonFlight.get("date_arrival");
                String price = (String) jsonFlight.get("price");
                Flight flight = new Flight(startingAirport, endingAirport, startingDate, endingDate, price);

                if(agency.get(flight.getStartingAirport()) == null)
                {
                    ArrayList<Flight> listOfFlights = new ArrayList<>();
                    agency.put(flight.getStartingAirport(), listOfFlights);
                }
                agency.get(flight.getStartingAirport()).add(flight);
            }
        } catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }
    }
}