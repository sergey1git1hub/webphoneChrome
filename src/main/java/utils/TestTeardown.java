package utils;

import callsMethods.Methods;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import static callsMethods.Methods.log;
import static callsMethods.Methods.logOut;
import static utils.Flags.isLocal;

import static utils.Logs.saveLogs;
import static utils.Video.moveOnTeardown;
import static utils.Video.moveVideo;


/**
 * Created by SChubuk on 03.01.2018.
 */
public class TestTeardown {
    public static void teardown(WebDriver driver, String testName) throws IOException, InterruptedException {
        saveLogs(driver, "b" + testName);
        logOut(driver);
        System.out.println(Boolean.getBoolean("closeBrowser"));
        if (Boolean.getBoolean("closeBrowser")) {
            Thread.sleep(2000);
            driver.quit();
            System.out.println("Quit method called.");
            boolean isIE = Flags.isIE(driver);
            String hostName = InetAddress.getLocalHost().getHostName();

            if (isIE) {
                Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            } else {
                if (!isLocal()) {
                    Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
                    System.out.println("Chrome Browser killed from teardown method.");
                }
            }
            Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
            log("3CXPhone killed from teardown method.", "DEBUG");
            Methods.fileWriter.close();
        }

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
            String hostName = InetAddress.getLocalHost().getHostName();

            if (isIE) {
                Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            } else {
                if (!isLocal()) {
                    Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
                }
            }
            Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
            log("3CXPhone killed from teardown method.", "DEBUG");
            Methods.fileWriter.close();
        }
    }


}
