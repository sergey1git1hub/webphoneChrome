package utils;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static actions.client.Client.openCXphone;
import static callsMethods.Methods.log;
import static utils.BeforeAfter.killPhoneAndDrivers;
import static utils.Logs.createLogFile;

/**
 * Created by SChubuk on 03.01.2018.
 */
public class TestSetup {
    public static File manualLogFile;

    public static void setup(String testName) throws InterruptedException, FindFailed, IOException {

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



}
