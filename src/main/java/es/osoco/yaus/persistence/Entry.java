package es.osoco.yaus.persistence;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class Entry {
    private String url;
    private String hash;

    public Entry(DBObject entry) {
        url = (String) entry.get("url");
        hash = ((ObjectId) entry.get("_id")).toString();
    }

    public String getUrl() {
        return url;
    }

    public String getHash() {
        return hash;
    }
}
