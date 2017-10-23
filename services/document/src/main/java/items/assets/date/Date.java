package items.assets.date;

public class Date {

    private int day;
    private int month;
    private int year;

    public Date(){}

    //TODO: do i need to implement my own DateComparator to verify or I can assert that the data is valid?
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
