package items.booking;

public enum Status {
    WAITING_APPROVAL("WAITING_APPROVAL"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    ARCHIVED("ARCHIVED");

    String str;

    Status(){}

    Status(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
