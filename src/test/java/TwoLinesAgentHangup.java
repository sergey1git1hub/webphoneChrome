import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.sikuli.script.FindFailed;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static data.Data.agentChrome;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class TwoLinesAgentHangup {
    static WebDriver driver;
    static Data data;

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void twoLinesAgentHangup() throws Exception {
        try {

            Methods.setup(driver);
            CallOnTwoLines.callOnTwoLines();
            driver = CallOnTwoLines.driver;
            data = CallOnTwoLines.data;
            Thread.sleep(1000);
            WebElement button_Hold = driver.findElement(By.cssSelector("#btn_hold"));
            button_Hold.click();
            Thread.sleep(1000);
            Methods.agentHangup(driver, 1);
            Thread.sleep(1000);
            Methods.agentHangup(driver, 2);
            Thread.sleep(1000);
            CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
            Methods.teardown(driver);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}

