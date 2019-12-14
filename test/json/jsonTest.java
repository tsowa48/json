package json;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tsowa
 */
public class jsonTest {

  /**
   * Test of toString method, of class json.
   */
  @Test
  public void testToString() {
    json J = new json("{ \"test\":\"welcome to me\", \"ya\":true, \"xyz\": {\"a\":74}, \"test2\":123, \"test3\":546.765}");
    Object Y = J.get("xyz");
    String actual;// = J.toString();
    //System.out.println("RET:'" + actual + "'");
    //assertEquals("{\"test\":\"welcome to me\",\"ya\":true,\"xyz\":{\"a\":74},\"test2\":123,\"test3\":546.765}", actual);
    
    //J = new json("{ \"test\":\"welcome to me\", \"ya\":true, \"xyz\": [74, 123, 546, 765]}");
    //Object X = J.get("xyz");
    //actual = J.toString();
    System.out.println("RET:'" + Y + "'");
    //assertEquals("{\"test\":\"welcome to me\",\"ya\":true,\"xyz\":{\"a\":74},\"test2\":123,\"test3\":546.765}", actual);
  }
}
