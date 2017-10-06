package network;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class MongoConnector {

    public static MongoCollection getBookings() {
        try {
            DB db = new MongoClient(Network.HOST, Network.PORT).getDB(Network.DATABASE);
            Jongo jongo = new Jongo(db);
            return jongo.getCollection(Network.COLLECTION);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
