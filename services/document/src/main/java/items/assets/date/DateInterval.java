package items.assets.date;

public class DateInterval {

    private Date departureDate;
    private Date returnDate;

    public DateInterval(Date departureDate, Date returnDate) {
        this.departureDate = departureDate;
        this.returnDate = returnDate;
    }

    public int getIntervalLength(){
        return this.returnDate.getAbsolute() - this.departureDate.getAbsolute();
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
