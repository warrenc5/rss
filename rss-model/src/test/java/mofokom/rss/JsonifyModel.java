/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mofokom.rss;

import devnull.common.jaxrs.JSON;
import java.math.BigInteger;
import javax.xml.namespace.QName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rss.Category;
import rss.Cloud;
import rss.Image;
import rss.RssChannel;
import rss.RssItem;
import rss.SkipDaysList;
import rss.TextInput;

/**
 *
 * @author wozza
 */
public class JsonifyModel {

    public JsonifyModel() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testModel() {
        RssChannel channel = JSON.objectify(JSON.loadFile("channel.json"),RssChannel.class);
/*
        RssItem item = new RssItem();
        Category category = new Category();
        category.setDomain("domain");
        channel.getCategory().add(category);
        channel.getItem().add(item);

        Cloud cloud = new Cloud();
        channel.setCloud(cloud);
        SkipDaysList skipDaysList = new SkipDaysList();
        channel.setSkipDays(skipDaysList);
        TextInput textInput = new TextInput();
        channel.setTextInput(textInput);
        channel.setTtl(BigInteger.valueOf(1L));
        Image image = new Image();
        channel.setImage(image);
        channel.getOtherAttributes().put(new QName("ns", "local"), "apple");
*/

        System.out.println(JSON.jsonify(channel));

    }
}
