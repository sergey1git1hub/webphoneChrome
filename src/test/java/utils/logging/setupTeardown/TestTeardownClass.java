package utils.logging.setupTeardown;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import utils.Flags;

import java.io.IOException;
import java.net.InetAddress;

import static actions.login.Logout.logOut;
import static callsMethods.Methods.log;
import static utils.BeforeAfter.killPhoneAndDrivers;
import static utils.Logs.saveLogs;
import static utils.Video.moveOnTeardown;


/**
 * Created by SChubuk on 03.01.2018.
 */
public class TestTeardownClass {
    public static void teardown(WebDriver driver, String testName) throws IOException, InterruptedException {
        saveLogs(driver, "b" + testName);
        logOut(driver);
        Thread.sleep(2000);
        driver.quit();
        killPhoneAndDrivers();
        moveOnTeardown();
    }


    public static void teardown(WebDriver driver, ITestContext ctx) throws IOException, InterruptedException {
        String testName = ctx.getCurrentXmlTest().getName();
        saveLogs(driver, "b" + testName);
        logOut(driver);
        if (Boolean.getBoolean("closeBrowser")) {
            Thread.sleep(2000);
            driver.quit();
            boolean isIE = Flags.isIE(driver);
            System.out.println("isIE: " + isIE);
            String hostName = InetAddress.getLocalHost().getHostName();

            if (isIE) {
                Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            } else {
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            }
            Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
            log("3CXPhone killed from teardown method.", "DEBUG");
        }
    }


}
