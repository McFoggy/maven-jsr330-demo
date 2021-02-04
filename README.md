# Maven JSR 330 : extension + plugin

## Build

### Project with extension + plugin

````
pushd extension-mojo
mvn clean install
popd
````

### Demo project

````
cd demo
mvn validate
````

### Problem

> Guice Injection did not occurred as expected

The output of the execution is:

````
[INFO] Scanning for projects...
[INFO]
[INFO] --------------------< fr.brouillard.oss:maven-demo >--------------------
[INFO] Building maven demo 0
[INFO] --------------------------------[ pom ]---------------------------------
[INFO]
[INFO] --- maven-demo-extension:0:info (demo) @ maven-demo ---
[ERROR] No information provided
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.233 s
[INFO] Finished at: 2021-02-04T21:06:05+01:00
[INFO] ------------------------------------------------------------------------
````

we should have expected the plugin to output information stored by the extension at the startup of the build

- [See `InfoExtension` setting the information](https://github.com/McFoggy/maven-jsr330-demo/blob/master/extension-mojo/src/main/java/fr/brouillard/oss/maven/InfoExtension.java#L23)
- [Plugin `InfoMojo` accessing the information](https://github.com/McFoggy/maven-jsr330-demo/blob/master/extension-mojo/src/main/java/fr/brouillard/oss/maven/InfoMojo.java#L22)