package items;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Hotel {

    private String name;
    private int id;

    public Hotel() {
    }

    public Hotel(JSONObject data) {
        this.name = data.getString("name");
        this.id = data.getInt("id");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("name", name)
                .put("id", id);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}

