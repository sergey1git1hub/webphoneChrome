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

    public static boolean isThisBrowser(String browser){
       /* Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();*/
       String browserName = System.getProperty("browserName");
        if (browserName.equals(browser))
            return true;
        else return false;
    }


    public static boolean isIE(WebDriver driver) {
        return isThisBrowser("internet explorer");
    }
    public static boolean isIE() {
        return isThisBrowser("internet explorer");
    }
    public static boolean isChrome(WebDriver driver){
        return isThisBrowser("chrome");
    }
    public static boolean isChrome(){
        return isThisBrowser("chrome");
    }
    public static boolean isOpera(WebDriver driver){
        return isThisBrowser("opera");
    }
    public static boolean isOpera(){
        return isThisBrowser("opera");
    }

}
