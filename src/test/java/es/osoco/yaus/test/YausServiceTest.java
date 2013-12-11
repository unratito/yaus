package es.osoco.yaus.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.osoco.yaus.core.YausService;
import es.osoco.yaus.persistence.Entry;
import es.osoco.yaus.persistence.EntryDao;

@RunWith(Arquillian.class)
public class YausServiceTest {
    private static final String URL = "http://www.osoco.es";
    private static final String RANDOM_HASH = "a1b2c3d4e5f6g7h8i9j10k11";

    @Deployment
    @OverProtocol("Servlet 3.0")
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(MavenImporter.class)
                .loadPomFromFile("pom.xml", "arquillian").importBuildOutput()
                .as(WebArchive.class);
    }

    @Inject
    private YausService yausService;
    @Inject
    private EntryDao entryDao;

    @Before
    public void setUp() {
        entryDao.removeAll();
    }

    @Test
    public final void testGetUrlFound() {
        String hash = entryDao.insert(URL).getHash();

        String url = yausService.getUrl(hash);

        assertEquals(URL, url);
    }

    @Test
    public final void testGetUrlNotFound() {
        String url = yausService.getUrl(RANDOM_HASH);

        assertNull(url);
    }

    @Test
    public final void testAddUrl() {
        String hash = yausService.addUrl(URL);

        Entry entry = entryDao.findByHash(hash);
        assertEquals(URL, entry.getUrl());

        entry = entryDao.findByUrl(URL);
        assertEquals(hash, entry.getHash());
    }

    @Test
    public final void testAddDuplicateUrl() {
        String hash1 = yausService.addUrl(URL);

        String hash2 = yausService.addUrl(URL);

        assertEquals(hash1, hash2);
    }
}
