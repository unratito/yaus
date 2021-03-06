package es.osoco.yaus.persistence;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Stateless
public class EntryDao {
    @Inject
    @ConfigProperty(name = "mongo.db")
    private String dbName;
    @Inject
    @ConfigProperty(name = "mongo.collection")
    private String collectionName;
    @Inject
    @ConfigProperty(name = "OPENSHIFT_MONGODB_DB_USERNAME")
    private String dbUserName;
    @Inject
    @ConfigProperty(name = "OPENSHIFT_MONGODB_DB_PASSWORD")
    private String dbPassword;

    @Inject
    private MongoClient mongoClient;

    public Entry findByUrl(String url) {
        DBObject entry = getEntries().findOne(new BasicDBObject("url", url));

        if (entry != null) {
            return new Entry(entry);
        }

        return null;
    }

    public Entry findByHash(String hash) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(hash);
        } catch (IllegalArgumentException e) {
            return null;
        }

        DBObject entry = getEntries().findOne(
                new BasicDBObject("_id", objectId));

        if (entry != null) {
            return new Entry(entry);
        }

        return null;
    }

    public Entry insert(String url) {
        DBObject entry = new BasicDBObject("url", url);

        getEntries().insert(entry);

        return new Entry(entry);
    }

    public void removeAll() {
        getEntries().remove(new BasicDBObject());
    }

    private DBCollection getEntries() {
        DB db = mongoClient.getDB(dbName);

        if (dbUserName != null) {
            if (!db.authenticate(dbUserName, dbPassword.toCharArray())) {
                throw new RuntimeException("Invalid MongoDB credentials");
            }
        }

        return db.getCollection(collectionName);
    }
}
