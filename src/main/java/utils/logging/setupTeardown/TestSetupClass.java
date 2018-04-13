package utils.logging.setupTeardown;

import org.sikuli.script.FindFailed;
import utils.logging.LoggingService;

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
public class TestSetupClass {

    private LoggingService loggingService = LoggingService.getLoggingService();

    public  void setup(String testName) throws InterruptedException, FindFailed, IOException {

        loggingService.createLogFile(testName + " ");
        System.out.println(testName.toUpperCase());
        killPhoneAndDrivers();
        Thread.sleep(1000);
        openCXphone(30);
        log("OpenCXphone method called from setup method.", "DEBUG");
    }

}
