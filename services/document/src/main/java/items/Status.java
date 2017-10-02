package items;

public enum Status {
    WAITING_APPROVAL("WAITING_APPROVAL"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    String str;

    Status(){}

    Status(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
