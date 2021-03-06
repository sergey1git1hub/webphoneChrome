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


    public static boolean isIE(WebDriver driver) {
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        if (browserName.equals("internet explorer"))
            return true;
        else return false;
    }

    public static boolean isChrome(WebDriver driver){
        return !isIE(driver);
    }

}
