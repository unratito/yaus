package es.osoco.yaus.persistence;

import com.mongodb.DBObject;

public class Entry {
    private String url;
    private String hash;

    public Entry(DBObject entry) {
        url = (String) entry.get("url");
        hash = (String) entry.get("hash");
    }

    public String getUrl() {
        return url;
    }

    public String getHash() {
        return hash;
    }
}
