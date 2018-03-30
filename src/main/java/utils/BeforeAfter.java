package utils;

import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;

import static utils.Flags.isLocal;
import static utils.Logs.createFolder;
import static utils.Video.moveVideo;

/**
 * Created by SChubuk on 04.01.2018.
 */
public class BeforeAfter {


    public static void killDrivers() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM iedriverserver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
    }

    public static void deleteDirectories() throws IOException {

        File sourceDirectory = new File("video");
        FileUtils.deleteDirectory(sourceDirectory);
        if (isLocal()) {
            sourceDirectory = new File("videoAndLogs");
            FileUtils.deleteDirectory(sourceDirectory);
        }
    }

    @BeforeSuite
    public static void beforeSuite(ITestContext ctx) throws IOException, InterruptedException {
        killDrivers();
        System.setProperty("browserName", "chrome");
        if (isLocal()) {
            System.setProperty("webdriver.ie.driver.loglevel", "ERROR");

            if (ctx.getCurrentXmlTest().getSuite().getName().equalsIgnoreCase("transfer")) {
                System.setProperty("folderName", "transfer");
            } else if(ctx.getCurrentXmlTest().getSuite().getName().equalsIgnoreCase("supervisor")){
                System.setProperty("folderName", "supervisor");
            } else {
                System.setProperty("folderName", System.getProperty("browserName"));
            }

        }
        deleteDirectories();
        createFolder();
    }

    @AfterSuite
    public static void afterSuite() throws IOException, InterruptedException {
        moveVideo();
    }


}
