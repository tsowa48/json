# json
__Simple JSON parser__

![json.jar][ico-build-size]
[![json.jar][ico-download]](https://github.com/tsowa48/json/raw/master/dist/json.jar)

Example:

```java
json jsonInstance = new json("SOME_JSON_DATA"); // Create new instance and parse
Object returnedObject = jsonInstance.get("KEY"); // Get data by KEY
String stringJsonRepresentation = jsonInstance.toString(); // Convert json to String
```

[ico-build-size]: https://img.shields.io/github/size/tsowa48/json/dist/json.jar
[ico-download]: https://img.shields.io/badge/download-json.jar-success
