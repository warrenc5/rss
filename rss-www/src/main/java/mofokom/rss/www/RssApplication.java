package mofokom.rss.www;

import mofokom.rss.session.RssSessionJAXR;
import devnull.common.jaxrs.exception.mapper.NoResultExceptionMapper;
import devnull.common.jaxrs.exception.mapper.ThrowableExceptionMapper;
import devnull.common.jaxrs.exception.mapper.ViewExceptionMapper;
import devnull.common.jaxrs.exception.mapper.WebApplicationExceptionMapper;
import java.util.Arrays;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
//import org.jboss.resteasy.plugins.interceptors.CorsFilter;

@ApplicationPath("rest")
@Stateless
public class RssApplication extends Application {

    @EJB
    RssSessionJAXR rssView;

    public RssApplication() {

    }

    @Override
    public Set<Object> getSingletons() {
        Set resources = new java.util.HashSet(Arrays.asList(rssView));
        //Set resources = new java.util.HashSet();
        return resources;
    }

    @Override
    public Set<Class<?>> getClasses() {
        return new java.util.HashSet(Arrays.asList(NoResultExceptionMapper.class, WebApplicationExceptionMapper.class, ViewExceptionMapper.class, ThrowableExceptionMapper.class));
    }

}
