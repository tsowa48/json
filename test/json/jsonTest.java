package json;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;

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
  
  /**
   * Test of toString method, of class json.
   */
  @Test
  public void testToString() {
    json J = new json("{ \"test\":\"welcome to me\", \"ya\": true, \"xyz\": {\"a\":74}, \"test2\":123, \"test3\":546.765}");
    Object Y = J.get("xyz").get("a").getValue();
    String actual = J.toString();
    assertEquals(74, Y);
    assertEquals("{\"test\":\"welcome to me\",\"ya\":true,\"xyz\":{\"a\":74},\"test2\":123,\"test3\":546.765}", actual);
    
    J = new json("{ \"xyz\": [{\"a\":74},{\"a\":132}], \"ya\":[82,156]}");
    actual = J.toString();
    assertEquals("{\"xyz\":[{\"a\":74},{\"a\":132}],\"ya\":[82,156]}", actual);
    
    J = new json("{ \"xyz\": [74,123,546,765], \"ya\":true}");
    json X = J.get("xyz");
    Object[] obj = (Object[])X.getValue();
    actual = Arrays.stream(obj)
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
    T2 t2 = J.toClass(T2.class);
    assertEquals("int=15 str=testStringJSON", t2.toString());
  }

}
