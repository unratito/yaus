package es.osoco.yaus.persistence;

import javax.ejb.Stateless;

@Stateless
public class EntryDao {
    public Entry findByUrl(String url) {
        throw new UnsupportedOperationException();
    }

    public Entry findByHash(String hash) {
        throw new UnsupportedOperationException();
    }

    public void insert(String url, String hash) {
        throw new UnsupportedOperationException();
    }
}
