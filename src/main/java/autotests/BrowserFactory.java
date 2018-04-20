package autotests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.logging.Level;

/**
 * Created by SChubuk on 19.04.2018.
 */
public class BrowserFactory {

    private WebDriver driver;
    private String browserName = System.getProperty("browserName");

    public WebDriver getBrowser(boolean remote) throws Exception {

        if (browserName.equalsIgnoreCase("ie")) {
            System.setProperty("webdriver.ie.driver", "C:/iedriver32/IEDriverServer.exe");
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
            System.setProperty("webdriver.chrome.driver", "C:/seleniumdrivers/chromedriver.exe");

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
            if (remote) {
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
            } else {
                driver = new ChromeDriver(chromeOptions);
            }
        }

        if (browserName.equalsIgnoreCase("opera")) {
            System.setProperty("webdriver.chrome.driver", "C:/seleniumdrivers/operadriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Program Files\\Opera\\52.0.2871.64\\opera.exe");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver(capabilities);
        }

        return new ChromeDriver();
    }


    //Browser factory test
    public static void main(String[] args) {
        try {
            System.setProperty("browserName", "chrome");

            BrowserFactory browserFactory = new BrowserFactory();
            WebDriver driver = browserFactory.getBrowser(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}