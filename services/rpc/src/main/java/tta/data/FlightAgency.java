package tta.data;


import java.util.HashMap;
import java.util.ArrayList;


public class FlightAgency {
    private static HashMap<String, ArrayList<Flight>> agency = new HashMap<String, ArrayList<Flight>>();

    public static ArrayList<Flight> getAllFlightsFrom(String origin) {
        return agency.get(origin);
    }

    public static Flight getFlight(String start, String end, String date)
    {
        ArrayList<Flight> listOfFlights = agency.get(start);
        for(Flight flight: listOfFlights)
        {
            if(flight.getEndingAirport() == end && flight.getStartDate().startsWith(date))
                return flight;
        }
        return null;
    }

    static {
        /*
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("datasets\flights\flights-data.json"));
            JSONArray jsonArray = (JSONArray) obj;

            Iterator<JSONObject> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject jsonFlight = iterator.next();
                Flight flight = new Flight();
                flight.setStartingAirport((String) jsonFlight.get("airport_origin"));
                flight.setEndingAirport((String) jsonFlight.get("airport_destination"));
                flight.setStartDate((String) jsonFlight.get("date_departure"));
                flight.setEndDate((String) jsonFlight.get("date_arrival"));
                flight.setPrice((String) jsonFlight.get("price"));
                if(agency.get(flight.getStartingAirport()) == null)
                    ArrayList<Flight> listOfFlights = new ArrayList<>();
                agency.get(flight.getStartingAirport()).add(flight);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        agency.put("Lyon", new ArrayList<>());
        agency.put("Paris", new ArrayList<>());
        agency.get("Lyon").add(new Flight("Lyon", "Nice", "2017-10-25 20:15:00", "2017-10-25 21:05:00", "$ 40"));
        agency.get("Paris").add(new Flight("Paris", "Nice", "2017-10-25 20:15:00", "2017-10-25 22:10:53", "$ 30"));
    }
}