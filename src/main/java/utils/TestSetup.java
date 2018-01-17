package utils;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.net.InetAddress;

import static utils.Flags.isLocal;
import static callsMethods.Methods.openCXphone;

/**
 * Created by SChubuk on 03.01.2018.
 */
public class TestSetup {
    public static void setup(WebDriver driver) throws InterruptedException, FindFailed, IOException {
        if (Boolean.getBoolean("closeBrowser")) {
            Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
            Thread.sleep(2000); //might fix phone not opened problem
            System.out.println("3CXPhone killed from setup method.");
            String hostName = InetAddress.getLocalHost().getHostName();

            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            if (!isLocal()) {
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            }
            openCXphone(60);
            System.out.println("openCXphone method called from setup method.");
        }
    }
}
