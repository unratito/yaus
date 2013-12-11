package es.osoco.yaus.persistence;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Stateless
public class EntryDao {
    private static final String DB_NAME = "yaus";
    private static final String COLLECTION_NAME = "urls";

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
        DBObject entry = getEntries().findOne(
                new BasicDBObject("_id", new ObjectId(hash)));

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
        return mongoClient.getDB(DB_NAME).getCollection(COLLECTION_NAME);
    }
}
