# json
__Simple JSON parser__

[![License][ico-license]](https://opensource.org/licenses/Apache-2.0)
![json.jar][ico-build]
![json.jar][ico-build-size]
[![json.jar][ico-download]](https://github.com/tsowa48/json/raw/master/dist/json.jar)

Example:

```java
json jsonInstance = new json("{ \"jsonKey\":{ \"stringKey\": \"stringValue\"}}"); // Create new instance and parse
String returnedObject = jsonInstance.<json>get("jsonKey").<String>get("stringKey"); // Get data (etc String)
String stringJsonRepresentation = jsonInstance.toString(); // Convert json to String
MyClass myClass = jsonInstance.toClass(MyClass.class); // Convert json to MyClass (with subClasses init)
```

[ico-license]: https://img.shields.io/badge/License-Apache%202.0-blue.svg
[ico-build]: https://travis-ci.com/tsowa48/json.svg?branch=master
[ico-build-size]: https://img.shields.io/github/size/tsowa48/json/dist/json.jar
[ico-download]: https://img.shields.io/badge/download-json.jar-success
