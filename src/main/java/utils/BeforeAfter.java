package utils;

import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.*;
import java.util.Properties;

import static callsMethods.Methods.nicePrint;
import static utils.Flags.isLocal;
import static utils.Logs.confSikulilogs;
import static utils.Logs.createFolder;
import static utils.Video.deleteDirectoryAfterSuite;
import static utils.Video.moveDirectoryAfterSuite;
import static utils.Video.moveVideo;

/**
 * Created by SChubuk on 04.01.2018.
 */
public class BeforeAfter {

    public static void killDrivers() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM iedriverserver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
    }

   /* public static void deleteDirectories() throws IOException {

        File sourceDirectory = new File("video");
        FileUtils.deleteDirectory(sourceDirectory);
        if (Boolean.getBoolean(System.getProperty("videoAndLogs.deleteIfLocal"))) {
            sourceDirectory = new File("videoAndLogs");
            FileUtils.deleteDirectory(sourceDirectory);
        }
    }*/

    @BeforeSuite
    public static void beforeSuite(ITestContext ctx) throws IOException, InterruptedException {

        loadProperties();
        killDrivers();
        confSikulilogs();


       // if (isLocal()) {
            if (ctx.getCurrentXmlTest().getSuite().getName().equalsIgnoreCase("transfer")) {
                System.setProperty("folderName", "transfer");
            } else if (ctx.getCurrentXmlTest().getSuite().getName().equalsIgnoreCase("supervisor")) {
                System.setProperty("folderName", "supervisor");
            } else {
                System.setProperty("folderName", System.getProperty("browserName"));
            }

       // }

        createFolder();
        deleteDirectoryAfterSuite();
    }

    @AfterSuite
    public static void afterSuite() throws IOException, InterruptedException {
        moveDirectoryAfterSuite();
        deleteDirectoryAfterSuite();
    }

    public static void loadProperties() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("src\\resources\\autotest.properties");

            // load a properties file
            prop.load(input);

            for (String name : prop.stringPropertyNames()) {
                String value = prop.getProperty(name);
                System.setProperty(name, value);
                nicePrint(name + "=" + value);
            }

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
