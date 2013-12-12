package es.osoco.yaus.persistence;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import com.mongodb.MongoClient;

@Singleton
public class MongoClientInitializer {
    @Inject
    @ConfigProperty(name = "OPENSHIFT_MONGODB_DB_HOST")
    private String dbHost;
    @Inject
    @ConfigProperty(name = "OPENSHIFT_MONGODB_DB_PORT")
    private Integer dbPort;

    private MongoClient mongoClient;

    @PostConstruct
    void init() {
        try {
            if (dbHost != null) {
                mongoClient = new MongoClient(dbHost, dbPort);
            } else {
                mongoClient = new MongoClient();
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("Cannot connect to MongoDB", e);
        }
    }

    @Produces
    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
