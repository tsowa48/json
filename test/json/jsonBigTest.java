package json;

import org.junit.Assert;
import org.junit.Test;
/**
 *
 * @author tsowa
 */
public class jsonBigTest {
  static final String JSON = "{\n" +
"    \"is_dev\": 1,\n" +
"    \"error_code\": 0,\n" +
"    \"error_name\": \"Успешно\",\n" +
"    \"data\": {\n" +
"        \"list\": [{\n" +
"            \"name\": \"ОБЛАСТЬ\",\n" +
"            \"exe_production\": \"ИП от 25.09.2018\",\n" +
"            \"details\": \"Акт по делу об административном правонарушении от 15.06.2018 № 18 Постановление о взыскании исполнительского сбора ОГИБДД ОТД.МВД РОССИИ ПО РАЙОНУ\",\n" +
"            \"subject\": \"Штраф ГИБДД: 439,00 руб. Исполнительский сбор: 1000 руб.\",\n" +
"            \"department\": \"Енский РОСП 300000, ОБЛАСТЬ, РАЙОН, С., УЛ., Д.\",\n" +
"            \"bailiff\": \"ЧИКОВА Ю. А. +7(47)23<br>+7(47)24<br>\",\n" +
"            \"ip_end\": \"\",\n" +
"            \"pay\": [{\n" +
"                \"name\": \"oplatagosuslug.ru\",\n" +
"                \"url\": \"http:\\/\\/is.fssprus.ru\\/pay\\/?service=oplatagosuslug&pay=IP\",\n" +
"                \"maxpay\": 0\n" +
"            }, {\n" +
"                \"name\": \"\\u041f\\u0440\\u043e\\u043c\\u0441\\u0432\\u044f\\u0437\\u044c\\u0431\\u0430\\u043d\\u043a\",\n" +
"                \"url\": \"http:\\/\\/is.fssprus.ru\\/pay\\/?service=psb&pay=IP\",\n" +
"                \"maxpay\": 0\n" +
"            }, {\n" +
"                \"name\": \"ROBOKASSA\",\n" +
"                \"url\": \"http:\\/\\/is.fssprus.ru\\/pay\\/?service=robokassa&pay=IP\",\n" +
"                \"maxpay\": 0\n" +
"            }, {\n" +
"                \"name\": \"\\u0420\\u0424\\u0418 \\u0411\\u0430\\u043d\\u043a\",\n" +
"                \"url\": \"http:\\/\\/is.fssprus.ru\\/pay\\/?service=rfibank&pay=IP\",\n" +
"                \"maxpay\": 0\n" +
"            }]\n" +
"        }]\n" +
"    }\n" +
"}";

  static final String JSON2 = "{\n" +
"    \"is_dev\": 1,\n" +
"    \"error_name\": \"Успешно\",\n" +
"    \"data\": [{\n" +
"            \"name\": \"ОБЛАСТЬ\",\n" +
"            \"subject\": \"Штраф ГИБДД: 439,00 руб. Исполнительский сбор: 1000 руб.\",\n" +
"            \"ip_end\": \"\"\n" +
"    }]\n" +
"}";
  
    static final String JSON3 = "{\n" +
"    \"is_dev\": 1,\n" +
"    \"error_code\": [0, 1,4,7],\n" +
"    \"error_name\": \"Успешно\",\n" +
"}";

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
      
  @Test
  public void testJson() {
    json J = new json(JSON);
    String X = J.<json>get("data").<json[]>get("list")[0].<String>get("subject");
    Assert.assertEquals("Штраф ГИБДД: 439,00 руб. Исполнительский сбор: 1000 руб.", X);
  }
  
  @Test
  public void testJson2() {
    json J = new json("{ \"myInt\":456, \"myString\":\"zyX\"}");
    T2 x = J.toClass(T2.class);
    Assert.assertEquals("int=456 str=zyX", x.toString());
  }
  
  @Test
  public void testJson3() {
    userClass user = new userClass();
    json J = new json(user);
    Assert.assertEquals("{\"arrMyInteger\":[{\"myInt\":4,\"myString\":\"stre\"},{\"myInt\":2,\"myString\":\"132\"}],\"arr2\":[3,25,16],\"intMyInt\":123,\"strMyString\":\"testJsonSting\",\"dblMyDouble\":3.1415926,\"boolMyBool\":true,\"myInt\":465,\"myDouble\":2.71,\"myBoolean\":false,\"myObj\":null}",
            J.toString());
  }
}
