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
>mvn validate
[INFO] extension generated information: Build started at 1612470960461
[INFO] Scanning for projects...
[INFO]
[INFO] --------------------< fr.brouillard.oss:maven-demo >--------------------
[INFO] Building maven demo 0
[INFO] --------------------------------[ pom ]---------------------------------
[INFO]
[INFO] --- maven-demo-extension:0:info (demo) @ maven-demo ---
[ERROR] No information provided, Singleton injection might have failed
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.204 s
[INFO] Finished at: 2021-02-04T21:36:00+01:00
[INFO] ------------------------------------------------------------------------
````

we should have expected the plugin to output information stored by the extension at the startup of the build

- [See `InfoExtension` storing the information](https://github.com/McFoggy/maven-jsr330-demo/blob/master/extension-mojo/src/main/java/fr/brouillard/oss/maven/InfoExtension.java#L30)
- [Plugin `InfoMojo` accessing the information](https://github.com/McFoggy/maven-jsr330-demo/blob/master/extension-mojo/src/main/java/fr/brouillard/oss/maven/InfoMojo.java#L22)