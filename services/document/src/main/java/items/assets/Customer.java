package items.assets;

import org.json.JSONObject;

public class Customer {

    private String firstName;
    private String lastName;
    private String email;
    private int customerId;

    public Customer(){}

    public Customer(JSONObject data){
        System.out.println("Customer: " + data);
        this.firstName = data.getString("firstName");
        this.lastName = data.getString("lastName");
        this.email = data.getString("email");
        this.customerId = data.getInt("customerId");
    }

    public JSONObject toJson(){
        return new JSONObject()
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("email", email)
                .put("customerId", customerId);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (customerId != customer.customerId) return false;
        if (!firstName.equals(customer.firstName)) return false;
        if (!lastName.equals(customer.lastName)) return false;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + customerId;
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", customerId=" + customerId +
                '}';
    }

}
