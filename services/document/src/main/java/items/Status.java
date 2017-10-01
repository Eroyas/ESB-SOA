package items;

public enum Status {
    WAITING_APPROVAL("waiting for approval"),
    APPROVED("approved"),
    REJECTED("rejected");

    String str;

    Status(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
