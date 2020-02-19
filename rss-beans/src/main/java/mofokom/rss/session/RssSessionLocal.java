/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mofokom.rss.session;

import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.validation.constraints.NotNull;
import rss.Enclosure;
import rss.Rss;
import rss.RssChannel;
import rss.RssItem;

/**
 *
 * @author wozza
 */
public interface RssSessionLocal {

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    Rss addChannel(RssChannel channel) throws RssException;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    Enclosure addEnclosure(String guid, Enclosure enclosure) throws RssException;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    RssItem addItem(String title, RssItem item) throws RssException;

    List<String> getAllFeedNames() throws RssException;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    RssChannel getChannel(@NotNull String channelTitle) throws RssException;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    Rss getFeed(String channelTitle) throws RssException;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    RssChannel removeChannel(String channelTitle) throws RssException;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    Enclosure removeEnclosure(String Guid) throws RssException;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    RssItem removeItem(String title, String Guid) throws RssException;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    Rss renameFeed(String channelTitle, String newChannelName) throws RssException;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    RssChannel updateChannel(String channelTitle, RssChannel channel) throws RssException;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    RssItem updateItem(String title, RssItem item) throws RssException;
    
}
