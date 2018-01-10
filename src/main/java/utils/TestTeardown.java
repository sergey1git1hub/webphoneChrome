package utils;

import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.net.InetAddress;
import static utils.Flags.isLocal;

import static utils.Logs.saveLogs;


/**
 * Created by SChubuk on 03.01.2018.
 */
public class TestTeardown {
    public static void teardown(WebDriver driver, String testName) throws IOException, InterruptedException {
        saveLogs(driver, testName);
        Thread.sleep(2000);
        driver.quit();
        boolean isIE = Flags.isIE(driver);
        String hostName = InetAddress.getLocalHost().getHostName();

            if (isIE) {
                Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            } else {
                if (!isLocal()) {
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            }
        }
        Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
        System.out.println("3CXPhone killed from teardown method.");
    }



}
