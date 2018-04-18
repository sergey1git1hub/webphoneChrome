package utils;

import data.Data;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by SChubuk on 04.01.2018.
 */
public class Flags {

    public static boolean isLocal() throws UnknownHostException {
            String hostName = InetAddress.getLocalHost().getHostName();
            boolean isLocal = hostName.equalsIgnoreCase(Data.localhostName);
            return isLocal;

    }

    public static boolean isThisBrowser(WebDriver driver, String browser){
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        if (browserName.equals(browser))
            return true;
        else return false;
    }

    public static boolean isIE(WebDriver driver) {
        return isThisBrowser(driver, "internet explorer");
    }

    public static boolean isChrome(WebDriver driver){
        return isThisBrowser(driver, "chrome");
    }

    public static boolean isOpera(WebDriver driver){
        return isThisBrowser(driver, "opera");
    }

}
