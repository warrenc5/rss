/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mofokom.rss.www;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import rss.Rss;
import mofokom.rss.session.RssSessionLocal;

/**
 *
 * @author wozza
 */
public class RssServlet extends HttpServlet {

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    @EJB
    RssSessionLocal rssBean;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("Field request from " + request.getRemoteAddr() + " " + request.getContentType() + " " + request.getPathInfo() + " " + rssBean);
        try {
            String name = request.getPathInfo();
            if (name.startsWith("/"))
                name = name.substring(1);
            if (name.endsWith(".rss"))
                name = name.substring(0, name.indexOf(".rss"));
            if (name.endsWith(".xml"))
                name = name.substring(0, name.indexOf(".xml"));

            if (name.endsWith("/"))
                name = name.substring(0, name.length() - 1);

            log.info("serving feed - channel : " + name);

            Rss rssFeed = rssBean.getFeed(name);
            JAXBContext context = JAXBContext.newInstance(Rss.class.getPackage().getName(), this.getClass().getClassLoader());
            Marshaller m = context.createMarshaller();
            m.marshal(rssFeed, response.getOutputStream());

            response.getOutputStream().close();

        } catch (Exception ex) {
            Logger.getLogger(RssServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getServletInfo() {
        return "Rss Feed Servlet";
    }
}
