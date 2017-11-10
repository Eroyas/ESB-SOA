package fr.unice.polytech.esb.flows.flight.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightRequest {


    @JsonProperty("destination") private String endingAirport;
    @JsonProperty("date") private String date;
    @JsonProperty("origin") private String startingAirport;


    public String getStartingAirport() { return startingAirport; }
    public void setStartingAirport(String airport) { this.startingAirport = airport; }

    public String getEndingAirport() { return endingAirport; }
    public void setEndingAirport(String airport) { this.endingAirport = airport; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

}
