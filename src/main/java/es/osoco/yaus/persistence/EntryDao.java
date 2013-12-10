package es.osoco.yaus.persistence;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
        DBObject entry = getEntries().findOne(new BasicDBObject("hash", hash));

        if (entry != null) {
            return new Entry(entry);
        }

        return null;
    }

    public void insert(String url, String hash) {
        getEntries().insert(new BasicDBObject("url", url).append("hash", hash));
    }

    private DBCollection getEntries() {
        return mongoClient.getDB(DB_NAME).getCollection(COLLECTION_NAME);
    }
}
