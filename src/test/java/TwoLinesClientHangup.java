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

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void twoLinesClientHangup() throws Exception {
        try {
        Methods.setup(driver);
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
        Methods.teardown(driver);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    }


