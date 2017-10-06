package carrent;

/**
 * @author Maxime
 */
public class Agency {
    private String name;
    private String city;
    private CarType carType;
    private double price;


    public Agency(String name, String city, CarType carType, double price) {
        this.name = name;
        this.city = city;
        this.carType = carType;
        this.price = price;
    }

    public String getCity() {
        return city;
    }
}
