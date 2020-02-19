package mofokom.rss.session;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import rss.Enclosure;
import rss.Guid;
import rss.Rss;
import rss.RssChannel;
import rss.RssItem;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testMyModel() throws JAXBException, RssException {
        Map map = new HashMap();
        //map.put("openjpa.ConnectionDatabaseName" ,"test");
        //map.put("openjpa.jdbc.SynchronizeMappings", "add, deleteTableContents");
        EntityManagerFactory emf = OpenJPAPersistence.createEntityManagerFactory("myrss", "META-INF/persistence.xml", map);
        EntityManager em = emf.createEntityManager();
        RssSessionBean s = new RssSessionBean();
        s.em = em;
        //CHANNEL ADD
        RssChannel rssChannel = new RssChannel();

        rssChannel.setTitle("My Channel 1 Title");
        rssChannel.setDescription("The best channel on the web");
        rssChannel.setLink("http://www.wherethischannellives.com/");

        em.getTransaction().begin();
        s.addChannel(rssChannel);
        em.flush();
        em.getTransaction().commit();

        //ITEMS
        //ITEM 1
        RssItem rssItem1 = new RssItem();
        rssItem1.setTitle("THIS IS A NEW TUNE");
        Guid guid1 = new Guid();
        guid1.setValue("http://uniqueurl.com/et1234");
        rssItem1.setGuid(guid1);
        rssItem1.setLink("http://nowhere");

        em.getTransaction().begin();
        s.addItem(rssChannel.getTitle(), rssItem1);
        em.flush();
        em.getTransaction().commit();

        //ITEM 2
        RssItem rssItem2 = new RssItem();
        rssItem2.setTitle("WHERE IN TIME");
        Guid guid2 = new Guid();
        guid2.setValue("http://uniqueurl.com/et5678");
        rssItem2.setGuid(guid2);
        rssItem2.setLink("http://nowhere");

        em.getTransaction().begin();
        s.addItem(rssChannel.getTitle(), rssItem2);
        em.flush();
        em.getTransaction().commit();

        Enclosure enclosure = new Enclosure();
        enclosure.setUrl("http://70.38.103.145/sweetyfluffy.3gp");
        enclosure.setType("video/3gp");
        enclosure.setValue("some value");

        em.getTransaction().begin();
        s.addEnclosure(rssItem1.getGuid().getValue(), enclosure);
        em.flush();
        em.getTransaction().commit();

        //FEED
        Rss rssFeed = s.getFeed(rssChannel.getTitle());

        em.close();
        JAXBContext context = JAXBContext.newInstance(RssChannel.class.getPackage().getName(), this.getClass().getClassLoader());
        Marshaller m = context.createMarshaller();
        m.marshal(rssFeed, new File("/tmp/wow1.rss"));
    }
}
