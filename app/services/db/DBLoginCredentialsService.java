package services.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import models.LoginCredentials;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import play.Play;

import java.net.UnknownHostException;

/**
 * Created by Wojtek on 20/04/15.
 */


public class DBLoginCredentialsService extends BasicDAO<LoginCredentials, ObjectId> {
    static DBLoginCredentialsService dbLoginCredentialsService = null;

    private DBLoginCredentialsService(MongoClient mongo, Morphia morphia, String dbName) {
        super(mongo, morphia, dbName);
    }

    private static void createDBLoginCredentialsService() {
        String uriString = Play.application().configuration().getString("mongo.uri");
        String dbName = Play.application().configuration().getString("mongo.db");

        try {
            MongoClientURI mongoClientURI = new MongoClientURI(uriString);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            Morphia morphia = new Morphia();

            dbLoginCredentialsService = new DBLoginCredentialsService(mongoClient, morphia, dbName);
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        }
    }

    public static DBLoginCredentialsService getDbLoginCredentialsService(){
        if(dbLoginCredentialsService == null){
            createDBLoginCredentialsService();
        }
        return dbLoginCredentialsService;
    }

}
