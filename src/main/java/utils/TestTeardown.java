package utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import java.io.IOException;
import java.net.InetAddress;
import static callsMethods.Methods.log;
import static callsMethods.Methods.logOut;
import static utils.BeforeAfter.killPhoneAndDrivers;
import static utils.Flags.isChrome;
import static utils.Flags.isIE;
import static utils.Logs.saveLogs;
import static utils.TestSetup.killBrowser;
import static utils.Video.moveOnTeardown;


/**
 * Created by SChubuk on 03.01.2018.
 */
public class TestTeardown {
    public static void teardown(WebDriver driver, String testName) throws IOException, InterruptedException {
        try {
        saveLogs(driver, "b" + testName);
        logOut(driver);
        Thread.sleep(2000);
        driver.quit();
        killPhoneAndDrivers();
        moveOnTeardown();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void teardown(WebDriver driver, ITestContext ctx) throws Exception {
        String testName = ctx.getCurrentXmlTest().getName();
        saveLogs(driver, "b" + testName);
        logOut(driver);
        driver.quit();
        if (Boolean.getBoolean("closeBrowser")) {
            Thread.sleep(2000);
            killBrowser();
            Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
            log("3CXPhone killed from teardown method.", "DEBUG");
        }
    }


}
