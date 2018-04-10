package transfer_and_supervisor;

import callsMethods.CallOnTwoLines;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import java.net.URL;

import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;


@Listeners(VideoListener.class)
public class SeleniumRemote {
    static Data data;
    static WebDriver driver;
    static boolean fast = false;
    static int delay = 2;
    public static String testName = "Selenium Remote";

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void seleniumRemote() throws Exception {

        setup(driver, testName);
        CallOnTwoLines.login("Automation Call From Queue");
        driver = CallOnTwoLines.driver;
        data = CallOnTwoLines.data;
        Thread.sleep(5000);
        teardown(driver, testName);
    }

}
