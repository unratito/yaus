package es.osoco.yaus.core;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.osoco.yaus.persistence.Entry;
import es.osoco.yaus.persistence.EntryDao;

@Stateless
public class YausService {
    @Inject
    private EntryDao entryDao;

    public String getUrl(String hash) {
        Entry entry = entryDao.findByHash(hash);

        if (entry != null) {
            return entry.getUrl();
        }

        return null;
    }

    public String addUrl(String url) {
        Entry entry = entryDao.findByUrl(url);

        if (entry == null) {
            entry = entryDao.insert(url);
        }

        return entry.getHash();
    }
}
