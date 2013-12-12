package es.osoco.yaus.core;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import es.osoco.yaus.persistence.Entry;
import es.osoco.yaus.persistence.EntryDao;

@Stateless
@Path("/")
public class YausService {
    @Inject
    private EntryDao entryDao;

    @GET
    @Path("{hash}")
    @Produces("text/plain")
    public String getUrl(@PathParam("hash") String hash) {
        Entry entry = entryDao.findByHash(hash);

        if (entry != null) {
            return entry.getUrl();
        }

        return null;
    }

    @POST
    @Produces("text/plain")
    public String addUrl(@FormParam("url") String url) {
        Entry entry = entryDao.findByUrl(url);

        if (entry == null) {
            entry = entryDao.insert(url);
        }

        return entry.getHash();
    }
}
