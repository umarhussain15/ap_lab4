import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by Umar on 07-Mar-16.
 */
public class CrawlerTest extends TestCase {

        Crawler c= new Crawler();
    public void testLoad() throws Exception {
        c.load("F:\\Movies");
        assertEquals(null,c.index.get("vvvvvvvvvvvvvvvvvvvv"));
        assertNotNull(c.index.get("Movie4k.to"));
    }

}