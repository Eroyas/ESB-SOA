package items.bill;

import org.json.JSONObject;

public class Bill {

    private int code;

    public Bill(){}

    public Bill(int code) {
        this.code = code;
    }

    public Bill(JSONObject json) {
        this.code = json.getInt("code");
    }

    public JSONObject toJson(){
        return new JSONObject()
                .put("code", this.code);
    }

    public int getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        return code == bill.code;
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "code=" + code +
                '}';
    }

}
