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

## List of Options

A complete list of options and switches is listed below.

**Option:** `-host`  
**Description:** Hostname of server.  
**Default Value:** `localhost`

**Option:** `-port`  
**Description:** Port number of server.  
**Default Value:** `80` (without `-ssl` switch) and `443` (with `-ssl` switch).

**Switch:** `-ssl`  
**Description:** Enable SSL ("https://"). Does not take an argument.

**Option:** `-cr`  
**Description:** Context root of website.  
**Default Value:** `/WebGoat`

**Option:** `-un`  
**Description:** Username of website.  
**Default Value:** `testun`

**Option:** `-pw`  
**Description:** Password of website.  
**Default Value:** `testpw`

**Switch:** `-headless`  
**Description:** Enable headless mode for Chrome or Firefox. Does not take an argument.  
**Note:** Supported on Chrome 59+ or Firefox 55+ on Linux or Firefox 56+ on Windows/Mac.

**Switch:** `-chrome`  
**Description:** Use Chrome browser. Does not take an argument.  
**Note:** Cannot be specified in combination with the `-firefox` switch. If neither `-chrome` nor `-firefox` are specified, Firefox will be the default browser.

**Switch:** `-firefox`  
**Description:** Use Firefox browser. Does not take an argument.  
**Note:** Cannot be specified in combination with the `-chrome` switch. If neither `-chrome` nor `-firefox` are specified, Firefox will be the default browser.

**Option:** `-bin`  
**Description:** Absolute path to the Chrome or Firefox binary.  
**Note:** If left undefined, the driver will attempt to deduce the default location on the current system.

**Option:** `-driver`  
**Description:** Absolute path to the Chrome driver or Firefox driver (GeckoDriver).  
**Default Value (Chrome on Linux):** `/usr/lib/chromium-browser/chromedriver`  
**Default Value (Chrome on Mac OS X):** `/Applications/Google Chrome.app/Contents/MacOS/chromedriver`  
**Default Value (Chrome on Windows):** `C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe`  
**Default Value (Firefox on Linux):** `/usr/lib64/firefox/geckodriver`  
**Default Value (Firefox on Mac OS X):** `/Applications/Firefox.app/Contents/MacOS/geckodriver`  
**Default Value (Firefox on Windows):** `C:\Program Files\Mozilla Firefox\geckodriver.exe`  
**Note:** The latest drivers can be found here:
[Chrome](https://sites.google.com/a/chromium.org/chromedriver/downloads) | [Firefox](https://github.com/mozilla/geckodriver/releases)


## Usage Examples

**Goal:** Run script on https://www.webgoat.com using Chrome browser in headless mode.  
**Command:** `java -jar WebGoat-Selenium.jar -host www.webgoat.com -ssl -chrome -headless`

**Goal:** Run script on https://www.webgoat.com using Firefox browser.  
**Command:** ``java -jar WebGoat-Selenium.jar -host www.webgoat.com -ssl``

**Goal:** Run script on http://qa-machine:8080 using Firefox browser in headless mode.  
**Command:** ``java -jar WebGoat-Selenium.jar -host qa-machine -port 8080 -headless``
