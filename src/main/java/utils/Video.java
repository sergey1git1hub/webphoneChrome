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

/**
 * Created by SChubuk on 03.01.2018.
 */


public class Video {

    public static void moveVideo() throws IOException, InterruptedException {
        File sourceDirectory = new File("video");
        File destDirectory = new File(System.getProperty("path"));
        //Thread.sleep(100 * 1000);
        FileUtils.copyDirectory(sourceDirectory, destDirectory);
        Thread.sleep(1000);
        FileUtils.deleteDirectory(sourceDirectory);
    }
}
