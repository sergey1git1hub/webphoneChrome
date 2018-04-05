package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.sikuli.basics.Debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import static utils.Flags.isChrome;

/**
 * Created by SChubuk on 03.01.2018.
 */
public class Logs {
    //This clss responsible for logging
    public static String path;


    public static void createFolder() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        Date date = new Date();
        String folderName = System.getProperty("folderName");
        path = "videoAndLogs\\" + folderName + "VideoAndLogs" + dateFormat.format(date).replaceAll("\\s", "");
        new File(path).mkdirs();
        System.setProperty("path", path);
        System.setProperty("video.folder", path);
    }

    public static void saveLogs(WebDriver driverForLogs, String methodName) throws IOException {
        if (isChrome(driverForLogs)) {
            LogEntries logEntries = driverForLogs.manage().logs().get(LogType.BROWSER);
            /* FileWriter writer = new FileWriter(driverLog);
        return writer;*/
            File driverLog = createLogFile(methodName);
            FileWriter writer = new FileWriter(driverLog, true);
            for (LogEntry logEntry : logEntries.getAll()) {
                writer.write(logEntry.toString() + "\\n");
            }
            writer.close();
        }
    }

    public static File createLogFile(String methodName) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        Date date = new Date();
        String logFilePath = "video\\" + methodName + dateFormat.format(date) + ".log";
        File driverLog = new File(logFilePath);
        driverLog.getParentFile().mkdirs();
        driverLog.createNewFile();
        return driverLog;

    }

    public static ChromeOptions setChromeLogs() {
        System.setProperty("webdriver.chrome.verboseLogging", "false");
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);

        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs); //deprecated
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.merge(caps);
        return chromeOptions;
    }

    public static void confSikulilogs() throws IOException {
        //Debug.setLogFile("/video");

    }
}
