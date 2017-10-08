package items;

import org.json.JSONObject;

public class Identity {

    private String firstName;
    private String lastName;
    private String email;

    public Identity(){}

    public Identity(JSONObject data){
        System.out.println("Identity: " + data);
        this.firstName = data.getString("first_name");
        this.lastName = data.getString("last_name");
        this.email = data.getString("email");
    }

    JSONObject toJson(){
        return new JSONObject()
                .put("first_name", firstName)
                .put("last_name", lastName)
                .put("email", email);
    }

    @Override
    public String toString() {
        return "Identity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
