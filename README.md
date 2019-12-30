# json
__Simple JSON parser__

![json.jar][ico-build]
![json.jar][ico-build-size]
[![json.jar][ico-download]](https://github.com/tsowa48/json/raw/master/dist/json.jar)

Example:

```java
json jsonInstance = new json("SOME_JSON_DATA"); // Create new instance and parse
json returnedObject = jsonInstance.get("KEY"); // Get json data by KEY
Object value = returnedObject.getValue();// Get simple json value (null, boolean, int, double, string, array[])
String stringJsonRepresentation = jsonInstance.toString(); // Convert json to String
MyClass myClass = returnedObject.toClass(MyClass.class);// Convert json to MyClass (create new instance inside)
```

[ico-build]: https://travis-ci.com/tsowa48/json.svg?branch=master
[ico-build-size]: https://img.shields.io/github/size/tsowa48/json/dist/json.jar
[ico-download]: https://img.shields.io/badge/download-json.jar-success
