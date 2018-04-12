# WebGoat8-Selenium Project
This is a sample Selenium project that will walkthrough the [WebGoat 8](https://github.com/WebGoat/WebGoat) application and exploit it. It is intended to demonstrate the speed and accuracy of vulnerability detection that can be achieved by instrumenting an application (WebGoat 8 in this case) with the [Contrast](https://www.contrastsecurity.com/) Java agent.

## Build Instructions
To build this project, run the following [Maven](https://maven.apache.org/) command:
```
mvn clean install
```
Once the build is complete, there will be an aggregate JAR named `WebGoat-Selenium.jar` in the `target` directory. (This JAR contains all dependencies and can be distributed.)

## Prerequisites

In order to run the Selenium script, you need at least one of the following:

* The latest [Firefox](https://www.mozilla.org/en-US/firefox/new/) browser and [GeckoDriver](https://github.com/mozilla/geckodriver/releases). We recommend that you place the GeckoDriver binary in the same directory as the Firefox binary.
* The latest [Chrome](https://www.google.com/chrome/) browser and [ChromeDriver](https://sites.google.com/a/chromium.org/chromedriver/). We recommend that you place the ChromeDriver binary in the same directory as the Chrome binary.

## Usage Instructions
Before you can run the Selenium script, make sure WebGoat 8 is up and running. For example, if you're using Contrast to instrument the application, you would use a command similar to the following to start WebGoat:
```
java -Xmx3G -javaagent:/path/to/contrast.jar -Dcontrast.override.appname=WebGoat8 -Dcontrast.override.appversion=Test-01 -jar /path/to/webgoat-server-8.0.0.M3.jar
```
Once WebGoat is up, you can run the Selenium script for Chrome by using a command similar to the following
```
java -jar WebGoat-Selenium.jar -host test-machine -port 8080 -chrome -driver /opt/google/chrome/chromedriver -headless -un webgoat -pw webgoat
```
This command will start Chrome in headless mode (i.e., no GUI) and run the script on http://test-machine:8080/WebGoat using the username and password of "webgoat". A complete list of options and usage examples can be found by running
 ```
 java -jar WebGoat-Selenium.jar -help
 ```
