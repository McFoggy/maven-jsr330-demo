# Maven JSR 330 : extension + plugin

## Build

### Project with extension + plugin

````
pushd extension
mvn clean install
popd
````

### Project with mojo

````
pushd mojo
mvn clean install
popd
````

### Demo project

````
cd demo
mvn validate
````

In the demo project, the mojo uses information defined and exposed via a singleton filled by the extension.