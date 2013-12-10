package es.osoco.yaus.persistence;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;

import com.mongodb.MongoClient;

@Singleton
public class MongoClientInitializer {
    private MongoClient mongoClient;

    @PostConstruct
    void init() {
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Cannot connect to MongoDB", e);
        }
    }

    @Produces
    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
