package utils;

import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

import static callsMethods.Methods.log;
import static callsMethods.Methods.nicePrint;
import static utils.Flags.isLocal;
import static utils.Logs.confSikulilogs;
import static utils.Logs.createFolder;
import static utils.NativeServiceUpdate.updateNativeService;
import static utils.Video.*;

/**
 * Created by SChubuk on 04.01.2018.
 */
public class BeforeAfter {

    @BeforeSuite
    public static void beforeSuite(ITestContext ctx) throws IOException, InterruptedException {
        loadProperties(); //should be executed first!
        updateNativeService();
        killPhoneAndDrivers();
        confSikulilogs();

        if (ctx.getCurrentXmlTest().getSuite().getName().equalsIgnoreCase("transfer")) {
            System.setProperty("folderName", "transfer");
        } else if (ctx.getCurrentXmlTest().getSuite().getName().equalsIgnoreCase("supervisor")) {
            System.setProperty("folderName", "supervisor");
        } else {
            System.setProperty("folderName", System.getProperty("browserName"));
        }
        createFolder();
        deleteDirectoryBeforeSuite();
    }

    @AfterSuite
    public static void afterSuite() throws IOException, InterruptedException {
        moveDirectoryAfterSuite();
        deleteDirectoryAfterSuite();
    }

    public static void killPhoneAndDrivers() throws IOException {

        Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
        log("3CXPhone killed.", "DEBUG");
        if (Boolean.getBoolean("closeBrowser")) {
            if (System.getProperty("browserName").equalsIgnoreCase("ie"))
                Runtime.getRuntime().exec("taskkill /F /IM iedriverserver.exe");
            if (System.getProperty("browserName").equalsIgnoreCase("ie"))
                Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
            log("Drivers killed.", "DEBUG");
        }
    }

    public static void printSystemProperties() {
        Properties p = System.getProperties();
        Enumeration keys = p.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = (String) p.get(key);
            System.out.println(key + ": " + value);
        }
    }

    public static void loadProperties() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("src\\resources\\autotest.properties");

            // load a properties file
            prop.load(input);
           /* System.out.println("Properties before reading properties file");
            printSystemProperties();*/

            for (String name : prop.stringPropertyNames()) {
                String value = prop.getProperty(name);
                if (System.getProperty(name) == null) {
                    System.setProperty(name, value);
                    nicePrint(name + "=" + System.getProperty(name));
                }

            }

           /* System.out.println("Properties after reading properties file");
            printSystemProperties();*/

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
