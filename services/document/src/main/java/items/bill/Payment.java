package items.bill;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

public class Payment {

    private int paymentId;
    private int customerId;
    private int travelId;
    private double amount;
    private String reason;
    @JsonProperty("bill")
    private Bill bill;

    public Payment(){}

    public Payment(int paymentId, int customerId, int travelId, int amount, Bill bill, String reason) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.travelId = travelId;
        this.amount = amount;
        this.reason = reason;
        this.bill = bill;
    }

    public Payment(JSONObject json){
        this.paymentId = json.getInt("paymentId");
        this.customerId = json.getInt("customerId");
        this.travelId = json.getInt("travelId");
        this.amount = json.getInt("amount");
        this.bill = new Bill(json.getJSONObject("bill"));
        this.reason = json.getString("reason");
    }

    public JSONObject toJson(){
        return new JSONObject()
                .put("paymentId", paymentId)
                .put("customerId", customerId)
                .put("travelId", travelId)
                .put("amount", amount)
                .put("bill", bill.toJson());
    }

    public int getPaymentId() {
        return paymentId;
    }

    public Bill getBill() {
        return bill;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getTravelId() {
        return travelId;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (paymentId != payment.paymentId) return false;
        if (customerId != payment.customerId) return false;
        if (travelId != payment.travelId) return false;
        if (Double.compare(payment.amount, amount) != 0) return false;
        if (reason != null ? !reason.equals(payment.reason) : payment.reason != null) return false;
        return bill != null ? bill.equals(payment.bill) : payment.bill == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = paymentId;
        result = 31 * result + customerId;
        result = 31 * result + travelId;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (bill != null ? bill.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", customerId=" + customerId +
                ", travelId=" + travelId +
                ", amount=" + amount +
                ", bill=" + bill +
                '}';
    }

}
