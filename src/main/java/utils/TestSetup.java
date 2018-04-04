package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.testng.ITestContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;

import static callsMethods.Methods.log;
import static utils.Flags.isLocal;
import static callsMethods.Methods.openCXphone;
import static utils.Logs.createLogFile;

/**
 * Created by SChubuk on 03.01.2018.
 */
public class TestSetup {
    public static FileWriter fileWriter;

    public static void setup(WebDriver driver, String testName) throws InterruptedException, FindFailed, IOException {

        fileWriter = createLogFile(testName + " ");
        fileWriter.write(testName.toUpperCase() + "\n");
        System.out.println(testName.toUpperCase());


        if (Boolean.getBoolean("closeBrowser")) {
            Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
            Thread.sleep(2000); //might fix phone not opened problem
            log("3CXPhone killed from setup method.", "DEBUG");
            String hostName = InetAddress.getLocalHost().getHostName();

            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            if (!isLocal()) {
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
               // System.out.println("Chrome Browser killed from setup method.");
            }
            openCXphone(60);
            log("OpenCXphone method called from setup method.", "DEBUG");
        }
    }


    public static void setup(WebDriver driver, ITestContext ctx) throws InterruptedException, FindFailed, IOException {
        String testName = ctx.getCurrentXmlTest().getName();

        fileWriter = createLogFile(testName + " ");
        fileWriter.write(testName.toUpperCase() + "\n");
        System.out.println(testName.toUpperCase());


        if (Boolean.getBoolean("closeBrowser")) {
            Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
            Thread.sleep(2000); //might fix phone not opened problem
            log("3CXPhone killed from setup method.", "DEBUG");
            String hostName = InetAddress.getLocalHost().getHostName();

            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            if (!isLocal()) {
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            }
            openCXphone(60);
            log("OpenCXphone method called from setup method.", "DEBUG");
        }
    }

}
