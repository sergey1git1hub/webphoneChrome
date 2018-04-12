package utils.logging;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.TestSetup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import static utils.Flags.isChrome;

/**
 * Created by SChubuk on 12.04.2018.
 */
public class LoggingService {
    private File file;
    private File folder;
    private static LoggingService loggingService;

    public  void log(String text, String logLevel) { //INFO, DEBUG, ERROR

        String LOGLEVEL = System.getProperty("LOGLEVEL");
        if (logLevel.equalsIgnoreCase(LOGLEVEL)||logLevel.equalsIgnoreCase("CONSOLE")) {
            System.out.println(text);
            writeLog(text);
        }

    }


    //open and close file here
    private  void writeLog(String text) {
        if (Boolean.getBoolean("withPound")) {
            text = "# " + text;
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(text + "\n");
            writer.close();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    private LoggingService(){
        createFolder();
    }

    public static LoggingService getLoggingService(){
        if(loggingService!=null){
            loggingService = new LoggingService();
        } else {
            loggingService =  new LoggingService();
        }
        return loggingService;
    }

    public  void createFolder() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        Date date = new Date();
        String folderName = System.getProperty("folderName");
        String path = "videoAndLogs\\" + folderName + "VideoAndLogs" + dateFormat.format(date).replaceAll("\\s", "");
        File folder = new File(path);
        folder.mkdir();
        this.folder = folder;
    }



    public void  createLogFile(String methodName) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        Date date = new Date();
        File file = new File(folder, methodName + ".log");
        file.mkdir();
        file.createNewFile();
        this.file = file;

    }


   /* public static void saveLogs(WebDriver driverForLogs, String methodName) throws IOException {
        if (isChrome(driverForLogs)) {
            LogEntries logEntries = driverForLogs.manage().logs().get(LogType.BROWSER);
            *//* FileWriter writer = new FileWriter(driverLog);
        return writer;*//*
            File driverLog = createLogFile(methodName);
            FileWriter writer = new FileWriter(driverLog, true);
            for (LogEntry logEntry : logEntries.getAll()) {
                writer.write(logEntry.toString() + "\\n");
            }
            writer.close();
        }
    }*/


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

    //test
    public static void main(String[] args) throws Exception {
        LoggingService loggingService = LoggingService.getLoggingService();
        loggingService.createLogFile("testFile");
        loggingService.log("testString", "INFO");
    }
}
