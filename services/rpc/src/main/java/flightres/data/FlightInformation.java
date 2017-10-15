package flightres.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class FlightInformation {

    private String startingAirport;
    private String endingAirport;
    private String date;
    private float price;

    @XmlElement
    public String getStartingAirport() { return startingAirport; }
    public void setStartingAirport(String airport) { this.startingAirport = airport; }

    @XmlElement
    public String getEndingAirport() { return endingAirport; }
    public void setEndingAirport(String airport) { this.endingAirport = endingAirport; }

    @XmlElement
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    @XmlElement
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
}
