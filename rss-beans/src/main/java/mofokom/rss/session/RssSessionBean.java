package mofokom.rss.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.jboss.ws.api.annotation.WebContext;
import rss.Enclosure;
import rss.Rss;
import rss.RssChannel;
import rss.RssItem;

/**
 *
 * @author wozza
 */
@Stateless(name = "RssService",
        mappedName = "RssServiceName")
@Local(value = {RssSessionLocal.class, RssSessionJAXR.class, RssSessionJAXWS.class})
@DeclareRoles(value = {"SUPER", "ADMIN", "USER", "anonymous"})
@WebContext(contextRoot = "/rss-ws", urlPattern = "/rss-service/", authMethod = "BASIC", secureWSDLAccess = false)
@WebService(endpointInterface = "mofokom.rss.session.RssSessionJAXWS",
        serviceName = "RssService",
        portName = "RssPort")
public class RssSessionBean implements RssSessionLocal {

    @Resource
    EJBContext context;

    //@Resource(mappedName = "java:/RssEntityManagerFactoryName")
    @PersistenceUnit
    public EntityManagerFactory eMF;

    //@Resource(mappedName = "java:/RssEntityManagerName")
    @PersistenceContext(unitName = "myrss")
    EntityManager em;

    /*
    @PostConstruct
    public void doit() {
        System.out.println("********************" + eMF + " " + em);
        em = eMF.createEntityManager();
    }*/
    @Override
    public List<String> getAllFeedNames() throws RssException {
        //em = eMF.createEntityManager();
        Query q = em.createQuery("SELECT o FROM RssChannel o");
        List<RssChannel> result = null;
        List<String> result2 = new ArrayList<String>();

        try {
            result = (List<RssChannel>) q.getResultList();
        } catch (Exception x) {
            throw new RssException(x);
        }

        for (RssChannel i : result) {
            result2.add(i.getTitle());
        }
        em.close();
        return result2;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Rss renameFeed(String channelTitle, String newChannelName) throws RssException {
        //em = eMF.createEntityManager();
        Query q = em.createQuery("SELECT f FROM Rss f where f.channel.title = :title");
        q.setParameter("title", channelTitle.trim());
        Rss result = null;

        try {
            result = (Rss) q.getSingleResult();
        } catch (Exception x) {
            throw new RssException(x);
        }

        for (RssItem i : result.getChannel().getItem()) {
            //load
        }

        result.getChannel().setTitle(newChannelName);
        em.merge(result);
        em.close();
        try {
            return (Rss) result.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RssException(ex);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Rss getFeed(String channelTitle) throws RssException {
        //em = eMF.createEntityManager();
        Query q = em.createQuery("SELECT f FROM Rss f where f.channel.title = :title");
        q.setParameter("title", channelTitle.trim());
        Rss result = null;
        try {
            result = (Rss) q.getSingleResult();
        } catch (Exception x) {
            throw new RssException(x);
        }

        for (RssItem i : result.getChannel().getItem()) {
            //load
        }
        em.close();
        try {
            return (Rss) result.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
            throw new RssException(ex);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @PermitAll
    public RssChannel getChannel(String channelTitle) throws RssException {
        //em = eMF.createEntityManager();
        Query q = em.createQuery("SELECT f FROM RssChannel f where f.title = :title");
        q.setParameter("title", channelTitle.trim());
        RssChannel result = null;
        try {
            result = (RssChannel) q.getSingleResult();
        } catch (Exception x) {
            throw new RssException(x);
        }

        for (RssItem i : result.getItem()) {
            //load
        }
        //em.close();
        try {
            return (RssChannel) result.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RssException(ex);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public RssChannel removeChannel(String channelTitle) throws RssException {
        //em = eMF.createEntityManager();
        Query q = em.createQuery("SELECT f FROM RssChannel f where f.title = :title");
        q.setParameter("title", channelTitle.trim());
        RssChannel result = null;
        try {
            result = (RssChannel) q.getSingleResult();
        } catch (Exception x) {
            throw new RssException(x);
        }
        for (RssItem i : result.getItem()) {
            //load
        }
        try {
            return (RssChannel) result.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RssException(ex);
        } finally {
            em.remove(result);
            //   em.close();
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @PermitAll
    public Rss addChannel(RssChannel channel) throws RssException {
        if (channel == null) {
            throw new RssException(new NullPointerException("no channel"));
        }

        if (!channel.getItem().isEmpty()) {
            throw new RssException("new channel cannot have items");
        }

        //em = eMF.createEntityManager();
        Rss rss = new Rss();
        rss.setChannel(channel);
        BigDecimal version = new BigDecimal(2.0).setScale(1);
        rss.setVersion(version);
        em.persist(rss);
        //em.flush();
        //em.close();
        return rss;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RssChannel updateChannel(String title, RssChannel channel) throws RssException {
        Query q = em.createQuery("SELECT c FROM RssChannel c where c.title = :title");
        q.setParameter("title", title);
        RssChannel result = null;

        try {
            result = (RssChannel) q.getSingleResult();
        } catch (Exception x) {
            throw new RssException(x);
        }

        if (!channel.getItem().isEmpty()) {
            throw new RssException("new channel cannot have items");
        }

        Rss rss = new Rss();
        rss.setChannel(channel);
        BigDecimal version = new BigDecimal(2.0).setScale(1);
        rss.setVersion(version);
        em.persist(channel);
        //em.flush();
        return channel;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public RssItem addItem(String title, RssItem item) throws RssException {

        //em = eMF.createEntityManager();
        Query q = em.createQuery("SELECT c FROM RssChannel c where c.title = :title");
        q.setParameter("title", title.trim());
        RssChannel result = null;
        try {
            result = (RssChannel) q.getSingleResult();
        } catch (Exception x) {
            throw new RssException(x);
        }

        result.getItem().add(item);
        System.out.println("added to channelLink " + title);

        em.merge(result);
        //em.close();

        return item;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public RssItem updateItem(String title, RssItem item) throws RssException {
        removeItem(title, item.getGuid().getValue());
        return addItem(title, item);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RssItem removeItem(String title, String Guid) throws RssException {

        //em = eMF.createEntityManager();
        Query q = em.createQuery("SELECT o FROM RssItem o where o.guid.value = :guid");
        q.setParameter("guid", Guid.trim());
        RssItem iResult = null;
        try {
            iResult = (RssItem) q.getSingleResult();
        } catch (Exception x) {
            throw new RssException(x);
        }

        q = em.createQuery("SELECT c FROM RssChannel c where c.title = :title");
        q.setParameter("title", title.trim());
        RssChannel result = null;
        try {
            result = (RssChannel) q.getSingleResult();
        } catch (Exception x) {
            throw new RssException(x);
        }

        if (iResult.getSource() != null) {
            em.remove(iResult.getSource());
        }
        if (iResult.getEnclosure() != null) {
            em.remove(iResult.getEnclosure());
        }

        em.remove(iResult);
        System.out.println("removed to channelLink " + title);
        result.getItem().remove(iResult);

        em.merge(result);
        //em.close();

        return iResult;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Enclosure addEnclosure(String guid, Enclosure enclosure) throws RssException {
        //em = eMF.createEntityManager();
        Query q = em.createQuery("SELECT c FROM RssItem c where c.guid.value = :guid");
        q.setParameter("guid", guid);
        RssItem result = null;
        try {
            result = (RssItem) q.getSingleResult();
        } catch (Exception x) {
            throw new RssException(x);
        }

        result.setEnclosure(enclosure);
        System.out.println("added to guid " + guid);

        em.merge(result);

        //em.close();
        return enclosure;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Enclosure removeEnclosure(String Guid) throws RssException {
        //em = eMF.createEntityManager();
        Query q = em.createQuery("SELECT c FROM RssItem c where c.guid.value = :param1");
        q.setParameter("param1", Guid.trim());
        RssItem result = null;
        try {
            result = (RssItem) q.getSingleResult();
        } catch (Exception x) {
            throw new RssException(x);
        }

        System.out.println("removing item enclosure " + Guid);

        Enclosure enclosure = result.getEnclosure();
        em.remove(enclosure);
        result.setEnclosure(null);

        em.merge(result);
        //em.close();

        return enclosure;
    }

}
