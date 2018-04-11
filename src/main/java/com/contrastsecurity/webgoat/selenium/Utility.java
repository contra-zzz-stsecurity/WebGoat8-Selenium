package com.contrastsecurity.webgoat.selenium;

public final class Utility {

    public static String getUrl(String host, String port, String contextRoot, boolean ssl) {
        String url;
        if (host.startsWith("http://") || host.startsWith("https://")) {
            if (port.equals("null"))
                return host;
            else
                return host + ":" + port;
        }
        if (ssl && (port.equals("null") || port.equals("443"))) {
            url = "https://" + host;
        } else if (!ssl && (port.equals("null") || port.equals("80"))) {
            url = "http://" + host;
        } else {
            url = "http" + (ssl ? "s://" : "://") + host + ":" + port;
        }
        return (contextRoot.equals("/") ? url : url + contextRoot);
    }

    public static String defaultDriverPath(boolean firefox) {
        String os = System.getProperty("os.name");
        if (os.contains("Windows"))
            os = "Windows";

        switch (os) {
            case "Linux":
                return (firefox ? "/usr/lib64/firefox/geckodriver" : "/usr/lib/chromium-browser/chromedriver");
            case "Mac OS X":
                return (firefox ? "/Applications/Firefox.app/Contents/MacOS/geckodriver" : "/Applications/Google Chrome.app/Contents/MacOS/chromedriver");
            case "Windows":
                return (firefox ? "C:\\Program Files\\Mozilla Firefox\\geckodriver.exe" : "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
            default:
                return (firefox ? "/usr/lib64/firefox/geckodriver" : "/usr/lib/chromium-browser/chromedriver");
        }
    }

    public static void help() {
        System.out.println("Option: -host\nDescription: Hostname of server.\nDefault Value: " + Main.host + "\n");
        System.out.println("Option: -port\nDescription: Port number of server.\nDefault Value: 80 (without -ssl switch) and 443 (with -ssl switch).\n");
        System.out.println("Switch: -ssl\nDescription: Enable SSL (\"https://\"). Does not take an argument.\n");
        System.out.println("Option: -cr\nDescription: Context root of website.\nDefault Value: " + Main.contextRoot + "\n");
        System.out.println("Option: -un\nDescription: Username of website.\nDefault Value: " + Main.un + "\n");
        System.out.println("Option: -pw\nDescription: Password of website.\nDefault Value: " + Main.pw + "\n");
        System.out.println("Switch: -headless\nDescription: Enable headless mode for Chrome or Firefox. Does not take an argument.\nNote: Supported on Chrome 59+ or Firefox 55+ on Linux or Firefox 56+ on Windows/Mac.\n");
        System.out.println("Switch: -chrome\nDescription: Use Chrome browser. Does not take an argument.\nNote: Cannot be specified in combination with the -firefox switch. If neither -chrome nor -firefox are specified, Firefox will be the default browser.\n");
        System.out.println("Switch: -firefox\nDescription: Use Firefox browser. Does not take an argument.\nNote: Cannot be specified in combination with the -chrome switch. If neither -chrome nor -firefox are specified, Firefox will be the default browser.\n");
        System.out.println("Option: -bin\nDescription: Absolute path to the Chrome or Firefox binary.\nNote: If left undefined, the driver will attempt to deduce the default location on the current system.\n");
        System.out.println("Option: -driver\nDescription: Absolute path to the Chrome driver or Firefox driver (GeckoDriver).");
        System.out.println("Default Value (Chrome on Linux): /usr/lib/chromium-browser/chromedriver");
        System.out.println("Default Value (Chrome on Mac OS X): /Applications/Google Chrome.app/Contents/MacOS/chromedriver");
        System.out.println("Default Value (Chrome on Windows): C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        System.out.println("Default Value (Firefox on Linux): /usr/lib64/firefox/geckodriver");
        System.out.println("Default Value (Firefox on Mac OS X): /Applications/Firefox.app/Contents/MacOS/geckodriver");
        System.out.println("Default Value (Firefox on Windows): C:\\Program Files\\Mozilla Firefox\\geckodriver.exe");
        System.out.println("Note: The latest drivers can be found here:");
        System.out.println("[Chrome] https://sites.google.com/a/chromium.org/chromedriver/downloads");
        System.out.println("[Firefox] https://github.com/mozilla/geckodriver/releases");
        System.out.println("\n\nUsage Examples\n--------------\n");
        System.out.println("Goal: Run script on https://www.WebGoat.com using Chrome browser in headless mode.");
        System.out.println("Command: java -jar WebGoat-Selenium.jar -host webgoat -ssl -chrome -headless\n");
        System.out.println("Goal: Run script on https://www.WebGoat.com using Firefox browser.");
        System.out.println("Command: java -jar WebGoat-Selenium.jar -host www.WebGoat.com -ssl\n");
        System.out.println("Goal: Run script on http://qa-machine:8080 using Firefox browser in headless mode.");
        System.out.println("Command: java -jar WebGoat-Selenium.jar -host qa-machine -port 8080 -headless");
    }
}
