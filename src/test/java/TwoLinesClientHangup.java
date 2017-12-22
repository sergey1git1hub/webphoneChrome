import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.sikuli.script.FindFailed;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class TwoLinesClientHangup {
    static Data data;
    static WebDriver driver;
    static boolean fast = false;
    static int delay = 2;

    @Test
    @Video
    public static void twoLinesClientHangup() throws InterruptedException, IOException, FindFailed {
        CallOnTwoLines.callOnTwoLines();
        driver = CallOnTwoLines.driver;
        data = CallOnTwoLines.data;
        if(fast == false){
        Methods.clientHangup(driver, 1);
        Thread.sleep(delay);
        Methods.clientHangup(driver, 2);
        Thread.sleep(delay);
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        } else{
            Methods.clientHangup(driver, 1);
            Methods.clientHangup(driver, 2);
            CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        }


    }

    @AfterClass
    @Video
    public void teardown() throws IOException {
        saveLogs(driver, "twoLinesClientHangup");
        boolean isIE = Methods.isIE(driver);
        driver.quit();

        if(isIE){
            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
        }
    }

    public static void saveLogs(WebDriver driver, String methodName) throws IOException {
        System.out.println(driver.toString());
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        Date date = new Date();
/*
        File driverLog = new File("video/" + methodName + dateFormat.format(date) + ".log");
*/
        File driverLog = new File("video\\" + methodName + dateFormat.format(date) + ".log");
        driverLog.getParentFile().mkdirs();
        driverLog.createNewFile();

        FileWriter writer = new FileWriter(driverLog);
        for (LogEntry logEntry : logEntries.getAll()) {
            writer.write(logEntry.toString() + "\\n");
        }
        writer.close();
     /*   for (LogEntry entry : logEntries) {
            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            //do something useful with the data
        }*/
    }
    }


