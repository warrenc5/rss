/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mofokom.rss.session;

import javax.ejb.ApplicationException;
import javax.xml.ws.WebFault;

/**
 *
 * @author wozza
 */

@WebFault(
  faultBean = "RssFault",
  targetNamespace = "http://microsyndicate.com/rss/"
)
@ApplicationException
public class RssException extends Exception {

    public RssException(String message) {
        super(message);
    }

    public RssException(String message, Throwable cause) {
        super(message, cause);
    }

    
    public RssException(Exception ex) {
        super(ex);
    }

}
