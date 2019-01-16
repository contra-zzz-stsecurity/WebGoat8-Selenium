package com.contrastsecurity.webgoat.selenium;

public class Main {
    static String un = "testun"; // Username
    static String pw = "testpw"; // Password
    static String host = "localhost"; // Hostname
    static String contextRoot = "/WebGoat"; // Context root
    static private String port ="null"; // Port
    static private boolean ssl = false; // HTTPS (true) or HTTP (false - default)
    static private boolean headless = false;
    static private boolean chrome = false;
    static private boolean firefox = false;
    static private String driverPath = "null";
    static private String browserBin = "null";
    static private boolean proxy = false;
    static String proxyHost = "127.0.0.1"; // Default proxy host
    static String proxyPort = "8080"; // Default proxy port 

    public static void main(String[] args) {
        String port_regex = "([0-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|99[0-9]|[1-8][0-9]{3}|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9]|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])";

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-help")) {
                Utility.help();
                return;
            }
        }
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-un":
                    if (i == args.length - 1 || args[i + 1].charAt(0) == '-')
                        throw new IllegalArgumentException("Expected argument after: " + args[i]);
                    un = args[i++ + 1];
                    break;
                case "-pw":
                    if (i == args.length - 1 || args[i + 1].charAt(0) == '-')
                        throw new IllegalArgumentException("Expected argument after: " + args[i]);
                    pw = args[i++ + 1];
                    break;
                case "-host":
                    if (i == args.length -1 || args[i + 1].charAt(0) == '-')
                        throw new IllegalArgumentException("Expected argument after: " + args[i]);
                    host = args[i++ + 1];
                    break;
                case "-port":
                    if (i == args.length -1 || args[i + 1].charAt(0) == '-')
                        throw new IllegalArgumentException("Expected argument after: " + args[i]);
                    if (!args[i + 1].matches(port_regex))
                        throw new IllegalArgumentException("Not a valid port: " + args[i + 1] + ". Valid ports are 0-65535.");
                    port = args[i++ + 1];
                    break;
                case "-ssl":
                    ssl = true;
                    break;
                case "-headless":
                    headless = true;
                    break;
                case "-proxy":
                    proxy = true;
                    break;
                case "-proxyHost":
                    if (i == args.length -1 || args[i + 1].charAt(0) == '-')
                        throw new IllegalArgumentException("Expected argument after: " + args[i]);
                    proxyHost = args[i++ + 1];
                    break;
                case "-proxyPort":
                    if (i == args.length -1 || args[i + 1].charAt(0) == '-')
                        throw new IllegalArgumentException("Expected argument after: " + args[i]);
                    if (!args[i + 1].matches(port_regex))
                        throw new IllegalArgumentException("Not a valid proxyPort: " + args[i + 1] + ". Valid ports are 0-65535.");
                    proxyPort = args[i++ + 1];
                    break;
                case "-driver":
                    if (i == args.length -1 || args[i + 1].charAt(0) == '-')
                        throw new IllegalArgumentException("Expected argument after: " + args[i]);
                    driverPath = args[i++ + 1];
                    break;
                case "-bin":
                    if (i == args.length -1 || args[i + 1].charAt(0) == '-')
                        throw new IllegalArgumentException("Expected argument after: " + args[i]);
                    browserBin = args[i++ + 1];
                    break;
                case "-cr":
                    if (i == args.length -1 || args[i + 1].charAt(0) == '-')
                        throw new IllegalArgumentException("Expected argument after: " + args[i]);
                    contextRoot = args[i++ + 1];
                    break;
                case "-chrome":
                    if (firefox) {
                        throw new IllegalArgumentException("Cannot specify both -chrome and -firefox switches.  Please specify only one.");
                    }
                    chrome = true;
                    break;
                case "-firefox":
                    if (chrome) {
                        throw new IllegalArgumentException("Cannot specify both -chrome and -firefox switches.  Please specify only one.");
                    }
                    firefox = true;
                    break;
                default:
                    throw new IllegalArgumentException("Not a valid option: " + args[i]);
            }
        }

        if (!chrome && !firefox) {
            firefox = true; // Set Firefox as default browser if browser not specified
        }
        if (driverPath.equals("null")) {
            driverPath = Utility.defaultDriverPath(firefox);
        }
        String baseUrl = Utility.getUrl(host, port, contextRoot, ssl);
        System.out.println("un: " + un);
        System.out.println("pw: " + pw);
        System.out.println("host: " + host);
        System.out.println("port: " + port);
        if (proxy){
            System.out.println("proxyHost: " + proxyHost);
            System.out.println("proxyPort: " + proxyPort);
        }
        System.out.println("ssl: " + ssl);
        System.out.println("url: " + baseUrl);
        if (firefox) {
            FirefoxScript.run(un, pw, baseUrl, headless, proxy, proxyHost, proxyPort, driverPath, browserBin);
        } else {
            ChromeScript.run(un, pw, baseUrl, headless, proxy, proxyHost, proxyPort, driverPath, browserBin);
        }
    }
}
