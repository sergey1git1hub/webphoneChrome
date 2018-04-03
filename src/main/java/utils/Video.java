package utils;

import com.automation.remarks.testng.VideoListener;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static callsMethods.Methods.log;

/**
 * Created by SChubuk on 03.01.2018.
 */


public class Video {
    static final File sourceDirectory = new File("video");

    public static void moveOnTeardown() throws IOException, InterruptedException {
       /* //Thread.sleep(5000);
        moveVideo();
        //Thread.sleep(5000);
        FileUtils.cleanDirectory(sourceDirectory);*/
    }

    public static void moveDirectoryAfterSuite() throws IOException, InterruptedException {
        //Thread.sleep(5000);
        moveVideo();
    }

    public static void deleteDirectoryAfterSuite() throws IOException, InterruptedException {
       // Thread.sleep(5000);
        FileUtils.deleteDirectory(sourceDirectory);
    }

    public static void moveVideo() throws IOException, InterruptedException {
        try {
            final File destDirectory = new File(System.getProperty("path"));
            System.out.println();
            /*log("Source directory is: " + sourceDirectory, "INFO");
            log("Destination directory is: " + destDirectory, "INFO");*/
            System.out.println("Source directory is: " + sourceDirectory);
            System.out.println("Destination directory is: " + destDirectory);
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    try {
                        FileUtils.copyDirectory(sourceDirectory, destDirectory);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t1.start();
            t1.join();
            //Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
