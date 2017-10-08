package flightres.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class SimpleItineraryRequest {

    /**
     * depart arrivee horraire,
     */
    private String identifier;
    //private float price;
    private String originCountry;
    private String destinationCountry;
    private String departureTime;

    @XmlElement(name = "id", required = true)
    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }

    /*
    @XmlElement(required=true)
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    */

    @XmlElement(required=true)
    public String getOriginCountry() { return originCountry; }
    public void setOriginCountry(String origin) { this.originCountry = origin; }


    @XmlElement(required=true)
    public String getDestinationCountry() { return destinationCountry; }
    public void setDestinationCountry(String destination) { this.destinationCountry = destination; }

    @XmlElement(required=true)
    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    @Override
    public String toString() {
        return "ItineraryRequest:\n  identifier: " + identifier + "\n  from: " + originCountry + "\n to: " + destinationCountry
                + "\n  depart time: " + departureTime;
    }

}