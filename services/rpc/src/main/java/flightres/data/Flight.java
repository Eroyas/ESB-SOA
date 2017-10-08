package flightres.data;

public class Flight {

    private String startingAirport;
    private String endingAirport;
    private String startDate;
    private String endDate;
    private String price;


    public Flight(String startingAirport, String endingAirport, String startDate, String endDate, String price) {
        this.startingAirport = startingAirport;
        this.endingAirport = endingAirport;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }


    public String getStartingAirport() { return startingAirport; }
    public void setStartingAirport(String startingAirport) { this.startingAirport = startingAirport; }

    public String getEndingAirport() { return endingAirport; }
    public void setEndingAirport(String endingAirport) { this.endingAirport = endingAirport; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String date) { this.startDate = date; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String date) { this.endDate = date; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
}
