import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

import static data.Data.agentChrome;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class TwoLinesAgentHangup {
    static WebDriver driver;
    static Data data;

    @Test
    @Video
    public static void twoLinesAgentHangup() throws InterruptedException, IOException, FindFailed {
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
        System.out.println(driver.toString());

    }

    @AfterClass(alwaysRun=true)
    @Video
    public void teardown() throws IOException {
        System.out.println(driver.toString());
        saveLogs(driver, "twoLinesAgentHangup");
        boolean isIE = Methods.isIE(driver);

        if(isIE){
            driver.quit();
            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
        } else{ driver.quit();}
    }

    @AfterSuite(alwaysRun = true)
    @Video
    public void closeCXphone() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
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

