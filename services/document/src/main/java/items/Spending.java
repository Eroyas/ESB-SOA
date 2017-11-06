package items;

import items.assets.Bill;

public class Spending {

    private double price;
    private Bill bill;

    public Spending(double price, Bill bill) {
        this.price = price;
        this.bill = bill;
    }

    public double getPrice() {
        return price;
    }

    public Bill getBill() {
        return bill;
    }

}
