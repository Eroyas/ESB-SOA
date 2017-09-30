package hotel;

/**
 * Created by Eroyas on 25/09/17.
 */
public class Hotel {

    private int id;
    private String name; // json : hotel_name (unique)
    private String city; // json : city
/*
    private String dateArrival;
    private String dateDeparture;
    private int numberPpl;
*/
    private String type; // json : hotel_type
    private double amount; // json : price_per_night

    public Hotel(int id, String name, String city, String type, double amount) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.type = type;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

/*
    public String getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(String dateArrival) {
        this.dateArrival = dateArrival;
    }

    public String getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(String dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public int getNumberPpl() {
        return numberPpl;
    }

    public void setNumberPpl(int numberPpl) {
        this.numberPpl = numberPpl;
    }
*/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                '}';
    }

}

