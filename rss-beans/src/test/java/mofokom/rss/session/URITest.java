package mofokom.rss.session;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import junit.framework.TestCase;

/**
 *
 * @author wozza
 */
public class URITest extends TestCase{
    public void testURIResolve() throws URISyntaxException, MalformedURLException{
        URI base = new URI("jar:file:/home/code/example-xforms/target/example/WEB-INF/lib/chiba-web-2.3.0-classes.jar!/xslt/html4.xsl");
        System.out.println(base.toString());
        System.out.println(base.getScheme());
        URI file = new URI(base.getSchemeSpecificPart());
        URI resolve = file.resolve("ui.xsl");
        resolve = new URI("jar",resolve.toString(),null);
        System.out.println(resolve.toString());
        assertEquals("jar:file:/home/code/example-xforms/target/example/WEB-INF/lib/chiba-web-2.3.0-classes.jar!/xslt/ui.xsl", resolve.toURL().toExternalForm());
    }
}
