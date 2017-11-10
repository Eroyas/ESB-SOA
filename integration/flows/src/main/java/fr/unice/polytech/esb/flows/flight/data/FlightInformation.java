
package fr.unice.polytech.esb.flows.flight.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class FlightInformation {
/*
 "origin": "Nice",
      "destination": "Paris",
      "date": "2017-08-14",
      "time": "12:30:00",
      "price": "89",
      "journeyType": "DIRECT",
      "duration": 92,
      "category": "ECO",
      "airline": "Air France"
*/
    @JsonProperty("origin") private String startingAirport;
    @JsonProperty("destination") private String endingAirport;
    @JsonProperty("date") private String date;
    @JsonProperty("price") private Double price;
    @JsonProperty("airline") private String airline;
    private String flightId;


    public FlightInformation(String startingAirport, String endingAirport, String date, Double price, String airline) {
        this.startingAirport = startingAirport;
        this.endingAirport = endingAirport;
        this.date = date;
        this.price = price;
        this.airline = airline;
        this.flightId = UUID.randomUUID().toString();
    }

    public FlightInformation() {
    }


    public String getStartingAirport() { return startingAirport; }
    public void setStartingAirport(String airport) { this.startingAirport = airport; }

    public String getEndingAirport() { return endingAirport; }
    public void setEndingAirport(String airport) { this.endingAirport = airport; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getAirline() { return airline; }
    public void setAirline(String company) { this.airline = company; }

    public String getFlightId() { return flightId; }
    public void setFlightId(String id) { this.flightId = id; }

    @Override
    public String toString() {
        return "FlightInformation{" +
                "startingAirport='" + startingAirport + '\'' +
                ", endingAirport='" + endingAirport + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", airline='" + airline + '\'' +
                ", flightId='" + flightId + '\'' +
                '}';
    }
}
