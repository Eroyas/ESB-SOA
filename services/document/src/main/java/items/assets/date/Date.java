package items.assets.date;

public class Date {

    private int day;
    // Every month is 30 days long for simplification
    private int month;
    private int year;

    public Date(){}

    //TODO: do i need to implement my own DateComparator to verify or I can assert that the data is valid?
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getAbsolute(){
        return this.day + this.month * 30 + this.year * 365;
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
