package json;

import com.sun.xml.internal.ws.util.StringUtils;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author tsowa
 */
public class jsonTest {

  public class userClass {
    private T2[] arrMyInteger;
    private Integer[] arr2;
    private Integer intMyInt;
    private String strMyString;
    private Double dblMyDouble;
    private Boolean boolMyBool;
    
    private int myInt;
    private double myDouble;
    private boolean myBoolean;
    
    private Object myObj = null;
    
    public userClass() {
      intMyInt = 123;
      strMyString = "testJsonSting";
      dblMyDouble = 3.1415926;
      boolMyBool = true;
      myInt = 465;
      myDouble = 2.71;
      myBoolean = false;
      arr2 = new Integer[] {3, 25, 16};
      arrMyInteger = new T2[] {new T2(4, "stre"), new T2(2, "132")};
    }
  }
  
  public class T2 {
    private Integer myInt = null;
    private String myString = null;
    
    public T2() { }
    public T2(Integer i, String s) {
      myInt = i;
      myString = s;
    }
    
    @Override
    public String toString() {
      return "int=" + myInt + " str=" + myString;
    }
  }
  /**
   * Test of toString method, of class json.
   */
  @Test
  public void testToString() {
    json J = new json("{ \"test\":\"welcome to me\", \"ya\":true, \"xyz\": {\"a\":74}, \"test2\":123, \"test3\":546.765}");
    Object Y = J.get("xyz");
    String actual = J.toString();
    assertEquals("{\"test\":\"welcome to me\",\"ya\":true,\"xyz\":{\"a\":74},\"test2\":123,\"test3\":546.765}", actual);
    
    J = new json("{ \"test\":\"welcome to me\", \"ya\":true, \"xyz\": [74, 123, 546, 765]}");
    Object[] X = (Object[])J.get("xyz");
    actual = Arrays.stream(X)
            .map(x -> String.valueOf((Integer)x))
            .collect(Collectors.joining(","));
    assertEquals("74,123,546,765", actual);
  }
  
  @Test
  public void testParseObj() {
    userClass user = new userClass();
    json J = new json(user);
    String actual = J.toString();
    assertEquals("{\"arrMyInteger\":[{\"myInt\":4,\"myString\":\"stre\"},{\"myInt\":2,\"myString\":\"132\"}],\"arr2\":[3,25,16],\"intMyInt\":123,\"strMyString\":\"testJsonSting\",\"dblMyDouble\":3.1415926,\"boolMyBool\":true,\"myInt\":465,\"myDouble\":2.71,\"myBoolean\":false,\"myObj\":null}", actual);
  }

  @Test
  public void testToObject() {
    json J = new json("{\"myInt\":15, \"myString\":\"testStringJSON\"}");
    Object t2 = J.toObject(new T2());
    T2 instance = (T2)t2;
    assertEquals("int=15 str=testStringJSON", instance.toString());
  }

}
