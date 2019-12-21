package json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author tsowa
 * 
 */
public class json extends Object {
  private final SimpleEntry<String, Object> entry;
  
  private SimpleEntry<String, Object> parse(String jsonString) {
    String part = jsonString.trim();
    String key = "";
    Object value = null;
    ArrayList<SimpleEntry<String, Object>> tempValue = new ArrayList<>();
    Integer currentPosition = 0;
    while(part.charAt(currentPosition) != '}') {
      String subPart = part.substring(currentPosition);
      Integer startKeyIndex = subPart.indexOf("\"") + 1;
      Integer endKeyIndex = subPart.indexOf("\"", startKeyIndex);
      String subKey = subPart.substring(startKeyIndex, endKeyIndex);
      currentPosition += (endKeyIndex + 2);
      subPart = subPart.substring(endKeyIndex + 2).trim();
      SimpleEntry<Object, Integer> subParse = parseSubValue(subPart);
      currentPosition += subParse.getValue();
      tempValue.add(new SimpleEntry<>(subKey, subParse.getKey()));
    }
    value = tempValue;
    return new SimpleEntry<>(key, value);
  }

  private SimpleEntry<Object, Integer> parseSubValue(String subPart) {
    Object subValue;
    Integer currentPosition = 0;
    if(subPart.startsWith("{")) {
      subValue = parse(subPart.substring(1));
      currentPosition = (subPart.indexOf("}") + 2);
    } else if(subPart.startsWith("[")) {
      ArrayList<Object> subValueArray = new ArrayList<>();
      currentPosition = subPart.length();
      subPart = subPart.substring(1).trim();
      while(subPart.length() > 1) {
        Integer subPosition = 0;
        SimpleEntry<Object, Integer> subParse = parseSubValue(subPart);
        subValueArray.add(subParse.getKey());
        subPosition = (subParse.getValue() + 1);
        subPart = subPart.substring(subPosition).trim();
      }
      subValue = subValueArray.toArray();
    } else if(subPart.startsWith("\"")) {//String (without quotes)
      Integer startStringValueIndex = 1;
      Integer endStringValueIndex = subPart.indexOf("\"", startStringValueIndex);
      subValue = subPart.substring(startStringValueIndex, endStringValueIndex);
      currentPosition = endStringValueIndex + 1;
    } else {//double, integer, bool, null
      Integer quoteIndex = subPart.indexOf(",");
      Integer square1Index = subPart.indexOf("}");
      Integer square2Index = subPart.indexOf("]");
      quoteIndex = quoteIndex < 0 ? Integer.MAX_VALUE : quoteIndex;
      square1Index = square1Index < 0 ? Integer.MAX_VALUE : square1Index;
      square2Index = square2Index < 0 ? Integer.MAX_VALUE : square2Index;
      Integer endIndex = Math.min(quoteIndex, Math.min(square1Index, square2Index));
      String notString = subPart.substring(0, endIndex).toLowerCase();
      if(notString.contains("."))
        subValue = Double.valueOf(notString);
      else if("null".equals(notString))
        subValue = null;
      else if("true".equals(notString))
        subValue = Boolean.TRUE;
      else if("false".equals(notString))
        subValue = Boolean.FALSE;
      else
        subValue = Integer.valueOf(notString);
      currentPosition = endIndex;
    }
    return new SimpleEntry<>(subValue, currentPosition);
  }
  
  private static SimpleEntry<String, Object> parseClass(Object obj) {
    try {
      String key = "";
      Object value = null;
      ArrayList<SimpleEntry<String, Object>> tempValue = new ArrayList<>();
      Class userClass = obj.getClass();
      Field[] fields = userClass.getDeclaredFields();
      for(int i = 0; i < fields.length; ++i) {
        Field field = fields[i];
        field.setAccessible(true);
        String subKey = field.getName();
        if(subKey.startsWith("this$"))
          continue;
        Object subValue = field.get(obj);
        if(subValue instanceof Object[])
          subValue = parseArrayClass(subValue);
        else if(!isSimpleClass(subValue))
          subValue = parseClass(subValue);
        tempValue.add(new SimpleEntry<>(subKey, subValue));
      };
      value = tempValue;
      return new SimpleEntry<>(key, value);
    } catch(IllegalAccessException iae) {
      return null;
    }
  }
  
  private static Object[] parseArrayClass(Object obj) {
    if(isSimpleArray(obj))
      return (Object[])obj;
    Object[] objArray = (Object[])obj;
    ArrayList<Object> subValue = new ArrayList<>();
    for(int i = 0; i < objArray.length; ++i)
      subValue.add(parseClass(objArray[i]));
    return subValue.toArray();
  }
  
  private static boolean isSimpleClass(Object obj) {
    return obj instanceof String ||
           obj instanceof Integer ||
           obj instanceof Boolean ||
           obj instanceof Double ||
           obj == null;
  }
  
  private static boolean isSimpleArray(Object obj) {
    return obj instanceof String[] ||
           obj instanceof Integer[] ||
           obj instanceof Boolean[] ||
           obj instanceof Double[];
    //TODO: List
  }
  
  private static String jsonToString(SimpleEntry<String, Object> jsonPart) {
    StringBuilder sb = new StringBuilder();
    String key = jsonPart.getKey();
    Object value = jsonPart.getValue();
    if(!key.isEmpty()) {
      sb.append("\"");
      sb.append(key);
      sb.append("\":");
    } else {
      sb.append("{");
    }
    if(value == null) {
      sb.append("null");
    } else {
      sb.append(valueToString(value));
    }
    if(key.isEmpty())
      sb.append("}");
    return sb.toString();
  }
  
  private static String valueToString(Object value) {
    if(value instanceof SimpleEntry) {
      return jsonToString((SimpleEntry)value);
    } else if(value instanceof Integer) {
      return String.valueOf((Integer)value);
    } else if(value instanceof Double) {
      return String.valueOf((Double)value);
    } else if(value instanceof Boolean) {
      return value.toString();
    } else if(value instanceof ArrayList) {
      ArrayList<SimpleEntry<String, Object>> subValue = (ArrayList)value;
      StringBuilder subBuilder = new StringBuilder();
      subValue.forEach(element -> { 
        subBuilder.append(jsonToString(element));
        subBuilder.append(",");
      });
      subBuilder.deleteCharAt(subBuilder.lastIndexOf(","));
      return subBuilder.toString();
    } else if(value instanceof Object[]) {
      StringBuilder subBuilder = new StringBuilder("[");
      Arrays.asList((Object[])value).forEach(element -> {
        subBuilder.append(valueToString(element));
        subBuilder.append(",");
      });
      subBuilder.deleteCharAt(subBuilder.lastIndexOf(","));
      subBuilder.append("]");
      return subBuilder.toString();
    } else {
      return "\"" + value.toString() + "\"";
    }
  }

  public json(Object obj) {
    if(obj instanceof String)
      entry = parse((String)obj);
    else
      entry = parseClass(obj);
  }
  
  public Object get(String key) {
    ArrayList<SimpleEntry<String, Object>> subEntry = (ArrayList)(entry.getKey().isEmpty() ? entry.getValue() : entry);
    Optional<Object> ret = subEntry.stream().filter(element -> key.equals(element.getKey())).map(element -> element.getValue()).findFirst();
    if(ret.isPresent())
      return ret.get();
    else
      return null;
  }
  
  @Override
  public String toString() {
    return jsonToString(entry);
  }
  
  public Object toObject(Object instance) {
    try {
      Object ret = instance;
      Field[] fields = instance.getClass().getDeclaredFields();
      for(int i = 0; i < fields.length; ++i) {
        Field field = fields[i];
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        String key = field.getName();
        Object value = this.get(key);
        if(!isSimpleClass(value))
          value = toObject(value.getClass());
        field.set(ret, value);
        field.setAccessible(isAccessible);
      }
      return ret;
    } catch(Exception ex) {
      return null;
    }
  }
}
