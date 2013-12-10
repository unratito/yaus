package es.osoco.yaus.core;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.osoco.yaus.persistence.Entry;
import es.osoco.yaus.persistence.EntryDao;

@Stateless
public class YausService {
    @Inject
    private EntryDao entryDao;

    public String getURL(String hash) {
        Entry entry = entryDao.findByHash(hash);

        if (entry != null) {
            return entry.getUrl();
        }

        return null;
    }

    public String addURL(String url) {
        Entry entry = entryDao.findByUrl(url);

        if (entry != null) {
            return entry.getHash();
        }

        String hash = "XXX"; // TODO: generate hash

        entryDao.insert(url, hash);

        return hash;
    }
}
