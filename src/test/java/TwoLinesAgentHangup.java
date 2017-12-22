import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.testng.annotations.*;

import java.io.IOException;

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

    }

    @AfterClass
    @Video
    public void teardown() throws IOException {
        Methods.saveLogs("twoLinesAgentHangup");
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
}

