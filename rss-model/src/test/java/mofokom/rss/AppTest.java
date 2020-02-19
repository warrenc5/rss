package mofokom.rss;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
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
    public void testMyModel() throws JAXBException {
        Map map = new HashMap();
        map.put("openjpa.ConnectionDatabaseName" ,"test");
        EntityManagerFactory emf = OpenJPAPersistence.createEntityManagerFactory("myrss", "META-INF/persistence.xml",map);
        EntityManager em = emf.createEntityManager();
        //FEED
        Rss rssFeed = new Rss();
        BigDecimal version= new BigDecimal(2.0).setScale(1);
        rssFeed.setVersion(version);


        //CHANNEL ADD
        RssChannel rssChannel = new RssChannel();
        rssFeed.setChannel(rssChannel);

        rssChannel.setTitle("Channel 1 Title");
        rssChannel.setDescription("The best channel on the web");
        rssChannel.setLink("http://www.wherethischannellives.com/");

        //ITEMS
        List<RssItem> itemsList = new ArrayList<RssItem>();
        rssChannel.setItem(itemsList);

        //ITEM 1
        RssItem rssItem1 = new RssItem();
        rssItem1.setTitle("THIS IS A NEW TUNE");
        Guid guid1 = new Guid();
        guid1.setValue("http://uniqueurl.com/1234");
        rssItem1.setGuid(guid1);
        rssItem1.setLink("http://nowhere");

        //ITEM 2
        RssItem rssItem2 = new RssItem();
        rssItem2.setTitle("WHERE IN TIME");
        Guid guid2 = new Guid();
        guid2.setValue("http://uniqueurl.com/5678");
        rssItem2.setGuid(guid2);
        rssItem2.setLink("http://nowhere");

        //ADD TO LIST
        itemsList.add(rssItem1);
        itemsList.add(rssItem2);

        Enclosure enclosure = new Enclosure();
        enclosure.setUrl("http://70.38.103.145/sweetyfluffy.3gp");
        enclosure.setType("video/3gp");
        enclosure.setValue("some value");
        rssItem1.setEnclosure(enclosure);


        em.getTransaction().begin();
        em.persist(rssFeed);
        em.flush();
        em.getTransaction().commit();


        Query q = em.createQuery("SELECT f FROM Rss f LEFT JOIN f.channel o where o.title = :title");
        String t = "Channel 1 Title";
        q.setParameter("title", t);
        
        rssFeed  = (Rss) q.getResultList().get(0);

        em.close();
        /*
        List<RssChannelAnyItem> list = new ArrayList<RssChannelAnyItem>();
        RssChannelAnyItem aitem = new RssChannelAnyItem();
        List<RssItemTitleOrDescriptionOrLinkItem> list3 = new ArrayList<RssItemTitleOrDescriptionOrLinkItem>();
        //rssFeed.setAnyItems(list2);


        list.add(aitem);
        aitem.setItem(rssItem1);
        RssItemTitleOrDescriptionOrLinkItem item2 = new RssItemTitleOrDescriptionOrLinkItem();
        item2.setItemTitle("Hello");
        //list3.add(rssItemRef1);
        itemsList.add(rssItem1);

*/


        /*
        rssItemBase1.add(rssItemRef1);
        rssItem1.setTitleOrDescriptionOrLinkItems(rssItemBase1);
        rssItemRef1.setItemEnclosure(enclosure);
        rssItemRef1.setItemTitle("hello");
        rssItemRef1.setItemDescription("nothing");
         */

        JAXBContext context = JAXBContext.newInstance(RssChannel.class.getPackage().getName(), this.getClass().getClassLoader());
        Marshaller m = context.createMarshaller();
        m.marshal(rssFeed, new File("/tmp/wow.rss"));


    }
    public void oldtestMyModel() throws JAXBException {

        /*
        //FEED
        Rss rssFeed = new Rss();
        BigDecimal version= new BigDecimal(2.0).setScale(1);
        rssFeed.setVersion(version);
       
        //CHANNEL ADD
        RssChannel rssChannel = new RssChannel();
        rssFeed.setChannel(rssChannel);

        List<RssChannelTitleOrLinkOrDescriptionItem> channelList = new ArrayList<RssChannelTitleOrLinkOrDescriptionItem>();
        rssChannel.setTitleOrLinkOrDescriptionItems(channelList);

        RssChannelTitleOrLinkOrDescriptionItem rssChannel1 = new RssChannelTitleOrLinkOrDescriptionItem();
        rssChannel1.setItemTitle("Channel 1 Title");
        rssChannel1.setItemDescription("The best channel on the web");
        rssChannel1.setItemLink("http://www.wherethischannellives.com/");
        channelList.add(rssChannel1);


        //ITEMS
        List<RssItem> itemsList = new ArrayList<RssItem>();
        rssChannel.setItem(itemsList);

        //ITEM 1
        RssItem rssItem1 = new RssItem();
        List<RssItemTitleOrDescriptionOrLinkItem> rssItemBase1 = new ArrayList<RssItemTitleOrDescriptionOrLinkItem>();
        RssItemTitleOrDescriptionOrLinkItem rssItemRef1 = new RssItemTitleOrDescriptionOrLinkItem();
        RssItemTitleOrDescriptionOrLinkItem rssItemRef1a = new RssItemTitleOrDescriptionOrLinkItem();
        rssItem1.setTitleOrDescriptionOrLinkItems(rssItemBase1);
        rssItemRef1.setItemTitle("THIS IS A TUNE");
        Guid guid1 = new Guid();
        guid1.setValue("1234");
        rssItemRef1a.setItemGuid(guid1);
        rssItemBase1.add(rssItemRef1);
        rssItemBase1.add(rssItemRef1a);

        //ITEM 2
        RssItem rssItem2 = new RssItem();
        List<RssItemTitleOrDescriptionOrLinkItem> rssItemBase2 = new ArrayList<RssItemTitleOrDescriptionOrLinkItem>();
        RssItemTitleOrDescriptionOrLinkItem rssItemRef2 = new RssItemTitleOrDescriptionOrLinkItem();
        rssItem2.setTitleOrDescriptionOrLinkItems(rssItemBase2);
        rssItemRef2.setItemTitle("WHERE IN TIME");
        Guid guid2 = new Guid();
        guid2.setValue("5678");
        rssItemRef2.setItemGuid(guid2);
        rssItemBase2.add(rssItemRef2);

        //ADD TO LIST

        itemsList.add(rssItem1);
        itemsList.add(rssItem2);

        /*
        List<RssChannelAnyItem> list = new ArrayList<RssChannelAnyItem>();
        RssChannelAnyItem aitem = new RssChannelAnyItem();
        List<RssItemTitleOrDescriptionOrLinkItem> list3 = new ArrayList<RssItemTitleOrDescriptionOrLinkItem>();
        //rssFeed.setAnyItems(list2);


        list.add(aitem);
        aitem.setItem(rssItem1);
        RssItemTitleOrDescriptionOrLinkItem item2 = new RssItemTitleOrDescriptionOrLinkItem();
        item2.setItemTitle("Hello");
        //list3.add(rssItemRef1);
        itemsList.add(rssItem1);

*/


        /*
        rssItemBase1.add(rssItemRef1);
        rssItem1.setTitleOrDescriptionOrLinkItems(rssItemBase1);
        Enclosure enclosure = new Enclosure();
        enclosure.setUrl("http://nowhere");
        rssItemRef1.setItemEnclosure(enclosure);
        rssItemRef1.setItemTitle("hello");
        rssItemRef1.setItemDescription("nothing");
         */

        /*JAXBContext context = JAXBContext.newInstance(RssChannel.class.getPackage().getName(), this.getClass().getClassLoader());
        Marshaller m = context.createMarshaller();
        m.marshal(rssFeed, new File("/tmp/wow.rss"));
*/
    }
}
