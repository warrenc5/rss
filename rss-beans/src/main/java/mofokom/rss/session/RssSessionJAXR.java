/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mofokom.rss.session;

import java.util.List;
import javax.ejb.Local;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import rss.Enclosure;
import rss.Rss;
import rss.RssChannel;
import rss.RssItem;

/**
 *
 * @author wozza
 */
@Local
@Path("rss")
public interface RssSessionJAXR extends RssSessionLocal {

    @POST
    @Path("channel/")
    @Produces(MediaType.APPLICATION_JSON)
    Rss addChannel(RssChannel channel) throws RssException;

    @POST
    @Path("enclosure/")
    Enclosure addEnclosure(@QueryParam("guid") String guid, Enclosure enclosure) throws RssException;

    @POST
    @Path("item/")
    RssItem addItem(@QueryParam("title") String title, RssItem item) throws RssException;

    @GET
    @Path("feeds/")
    List<String> getAllFeedNames() throws RssException;

    @GET
    @Path("channel/")
    RssChannel getChannel(@NotNull @QueryParam("channelTitle") String channelTitle) throws RssException, ConstraintViolationException;

    @GET
    @Path("feed/")
    Rss getFeed(@QueryParam("channelTitle") String channelTitle) throws RssException;

    @DELETE
    @Path("channel/")
    RssChannel removeChannel(@QueryParam("channelTitle") String channelTitle) throws RssException;

    @DELETE
    @Path("enclosure/")
    Enclosure removeEnclosure(@QueryParam("guid") String Guid) throws RssException;

    @DELETE
    @Path("item/")
    RssItem removeItem(@QueryParam("title") String title, String Guid) throws RssException;

    @POST
    @Path("feed/")
    Rss renameFeed(@QueryParam("channelTitle") String channelTitle, @QueryParam("newChannelName") String newChannelName) throws RssException;

    @POST
    @Path("channel/")
    RssChannel updateChannel(@QueryParam("title") String title, RssChannel channel) throws RssException;

    @POST
    @Path("item/")
    RssItem updateItem(@QueryParam("title") String title, RssItem item) throws RssException;

}
