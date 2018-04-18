package utils;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static callsMethods.Methods.log;
import static utils.BeforeAfter.killPhoneAndDrivers;
import static callsMethods.Methods.openCXphone;
import static utils.Flags.isChrome;
import static utils.Flags.isIE;
import static utils.Logs.createLogFile;

/**
 * Created by SChubuk on 03.01.2018.
 */
public class TestSetup {
    public static File manualLogFile;

    public static void setup(WebDriver driver, String testName) throws Exception {
        killBrowser();
        manualLogFile = createLogFile(testName + " ");
        FileWriter writer = new FileWriter(manualLogFile, true);
        writer.write(testName.toUpperCase() + "\n");
        writer.close();
        System.out.println(testName.toUpperCase());
        killPhoneAndDrivers();
        Thread.sleep(1000);
        openCXphone(30);
        log("OpenCXphone method called from setup method.", "DEBUG");
    }

    public static void killBrowser() throws Exception{
     if (Boolean.getBoolean("closeBrowser")) {
        Thread.sleep(2000);
        String browser = System.getProperty("browserName");

        if (isIE()) {
            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
        } else if (isChrome()) {
            Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
        } else {
            Runtime.getRuntime().exec("taskkill /F /IM opera.exe");
        }

        Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
        log("3CXPhone killed from teardown method.", "DEBUG");
    }
}



}
