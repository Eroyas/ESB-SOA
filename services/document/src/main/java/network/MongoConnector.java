package network;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class MongoConnector {

    public static MongoCollection getBookings() {
        try {
            DB db = new MongoClient("localhost", 27017).getDB("tta-database");
            Jongo jongo = new Jongo(db);
            return jongo.getCollection("bookings");
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
