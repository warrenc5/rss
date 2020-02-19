package mofokom.rss.session;

import java.util.List;
import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import mofokom.rss.session.RssException;
import mofokom.rss.session.RssSessionLocal;
import rss.Enclosure;
import rss.Rss;
import rss.RssChannel;
import rss.RssItem;

/**
 *
 * @author wozza
 */
@Remote
@WebService(name = "RssWebService",
targetNamespace = "http://microsyndicate.com/rss")
@SOAPBinding(style = Style.DOCUMENT,
use = Use.LITERAL,
parameterStyle = ParameterStyle.WRAPPED)
public interface RssSessionJAXWS extends RssSessionLocal {

    @WebMethod(operationName = "GetAllRssFeedNames",
    action = "GetAllRssFeedNames")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss",
    name = "AllRssFeedNames",
    partName = "AllRssFeedNames")
    List<String> getAllFeedNames() throws RssException;

    @WebMethod(operationName = "GetRssFeed",
    action = "GetRssFeed")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss",
    name = "RssFeed",
    partName = "RssFeed")
    Rss getFeed(@WebParam(name = "ChannelName") String channelLink) throws RssException;

    @WebMethod(operationName = "RenameRssFeed",
    action = "RenameRssFeed")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss",
    name = "renameFeed",
    partName = "renameFeed")
    Rss renameFeed(@WebParam(name = "ChannelName") String channelLink, @WebParam(name = "NewChannelName") String newChannelName) throws RssException;

    @WebMethod(operationName = "GetRssChannel",
    action = "GetRssChannel")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss",
    name = "RssChannel",
    partName = "RssChannel")
    RssChannel getChannel(@WebParam(name = "ChannelName") String channelLink) throws RssException;

    @WebMethod(operationName = "RemoveRssChannel",
    action = "RemoveRssChannel")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss",
    name = "RssChannel",
    partName = "RssChannel")
    RssChannel removeChannel(@WebParam(name = "ChannelName") String channelLink) throws RssException;

    @WebMethod(operationName = "AddChannel", action = "AddChannel")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss", name = "NewChannel", partName = "RssChannel")
    Rss addChannel(@WebParam(name = "RssChannel") RssChannel channel) throws RssException;
   
    @WebMethod(operationName = "UpdateChannel",
    action = "UpdateChannel")
    RssChannel updateChannel(@WebParam(name = "RssChannelTitle") String channelTitle, @WebParam(name = "RssChannel") RssChannel channel) throws RssException;

    @WebMethod(operationName = "AddItem",
    action = "AddItem")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss",
    name = "NewItem",
    partName = "RssItem")
    RssItem addItem(@WebParam(name = "RssChannelTitle") String channelLink, @WebParam(name = "RssItem") RssItem item) throws RssException;

    @WebMethod(operationName = "UpdateItem",
    action = "UpdateItem")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss",
    name = "NewItem",
    partName = "RssItem")
    RssItem updateItem(@WebParam(name = "RssChannelTitle") String channelLink, @WebParam(name = "RssItem") RssItem item) throws RssException;

    @WebMethod(operationName = "RemoveItem",
    action = "GetRssFeed")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss",
    name = "OldItem",
    partName = "RssItem")
    RssItem removeItem(@WebParam(name = "RssChannelTitle") String channelLink, @WebParam(name = "RssItemGuid") String Guid) throws RssException;

    @WebMethod(operationName = "AddEnclosure",
    action = "AddEnclosure")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss",
    name = "NewEnclosure",
    partName = "Enclosure")
    Enclosure addEnclosure(@WebParam(name = "RssItemGuid") String Guid, @WebParam(name = "Enclosure") Enclosure enclosure) throws RssException;

    @WebMethod(operationName = "RemoveEnclosure",
    action = "RemoveEnclosure")
    @WebResult(targetNamespace = "http://microsyndicate.com/rss",
    name = "OldEnclosure",
    partName = "Enclosure")
    Enclosure removeEnclosure(@WebParam(name = "RssItemGuid") String Guid) throws RssException;
}
