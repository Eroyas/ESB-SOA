package network;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class MongoConnector {

    private MongoClient mongoClient;

    public MongoConnector(){
        this.mongoClient = this.initMongoClient();
    }

    private MongoClient initMongoClient(){
        System.out.println("Opening new connection.");
        return new MongoClient(Network.HOST, Network.PORT);
    }

    public void closeClientConnection(){
        this.mongoClient.close();
        this.mongoClient = null;
    }

    public MongoCollection getBookings() {
        if (this.mongoClient == null){
            this.initMongoClient();
        }
        try {
            Jongo jongo = new Jongo(this.mongoClient.getDB(Network.DATABASE));
            return jongo.getCollection(Network.COLLECTION_BOOKINGS);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public MongoCollection getPayments() {
        if (this.mongoClient == null){
            this.initMongoClient();
        }
        try {
            Jongo jongo = new Jongo(this.mongoClient.getDB(Network.DATABASE));
            return jongo.getCollection(Network.COLLECTION_PAYMENTS);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
