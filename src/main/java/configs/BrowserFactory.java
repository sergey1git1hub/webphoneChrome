package configs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.logging.Level;

/**
 * Created by SChubuk on 19.04.2018.
 */
public class BrowserFactory {

    private WebDriver driver;
    private String browserName = System.getProperty("browserName");
    private String baseDriverPath = System.getProperty("baseDriverPath");
    private String operaBinaryPath = System.getProperty("operaBinary");

    public WebDriver getBrowser(boolean remote) throws Exception {

        if (browserName.equalsIgnoreCase("ie")) {
            System.setProperty("webdriver.ie.driver", baseDriverPath + "IEDriverServer.exe");
            DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
            ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                    true);

            /**********PLAY WITH CAPABILITIES*********************/
            //ieCapabilities.setCapability("initialBrowserUrl", webphoneUrl);
            ieCapabilities.setCapability("nativeEvents", false);
            //ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
            ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
            ieCapabilities.setCapability("disable-popup-blocking", true);
            ieCapabilities.setCapability("enablePersistentHover", true);
            ieCapabilities.setCapability("ignoreZoomSetting", true);
            /***************************************************/

            driver = new InternetExplorerDriver(ieCapabilities);

        }

        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", baseDriverPath + "chromedriver.exe");

            DesiredCapabilities caps = DesiredCapabilities.chrome();
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);

            caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs); //deprecated
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.merge(caps);

            if (Boolean.getBoolean("autoOpenDevtoolsForTabs")) {
                chromeOptions.addArguments("--auto-open-devtools-for-tabs");
            }
            chromeOptions.addArguments("--preserve-log");
            chromeOptions.addArguments("--lang=en");
            if (remote) {

                //Start hub and node using batch files
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
            } else {
                driver = new ChromeDriver(chromeOptions);
            }
        }

        if (browserName.equalsIgnoreCase("opera")) {
            System.setProperty("webdriver.chrome.driver", baseDriverPath + "operadriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Program Files\\Opera\\52.0.2871.64\\opera.exe");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver(capabilities);
        }

        return driver;
    }


    //Browser factory test
    public static void main(String[] args) {
        try {
            System.setProperty("browserName", "chrome");

            BrowserFactory browserFactory = new BrowserFactory();
            WebDriver driver = browserFactory.getBrowser(false);

            Thread.sleep(5000);
            driver.quit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void testDriverFactory(String browserName, boolean remote) throws Exception {
        System.setProperty("browserName", browserName);
        BrowserFactory browserFactory = new BrowserFactory();
        WebDriver driver = browserFactory.getBrowser(remote);
        Assert.assertNotNull(driver);
        Thread.sleep(10000);
        driver.quit();

    }


    /*@Test
    private void testChromeLocal() throws Exception {
        testDriverFactory("chrome", false);
    }*/

    @Test
    public void testChromeRemote() throws Exception {
        testDriverFactory("chrome", true);
    }

   /* @Test
    public void testIeLocal() {

    }

    @Test
    public void testIeRemote() {

    }

    @Test
    public void testOperaLocal() {

    }

    @Test
    public void testOperaRemote() {

    }*/


}
