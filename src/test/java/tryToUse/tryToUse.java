package tryToUse;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sikuli.script.FindFailed;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static utils.Logs.createFolder;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;
import static utils.Video.moveVideo;

/**
 * Created by SChubuk on 04.01.2018.
 */
@Listeners(VideoListener.class)
public class tryToUse {

    @Test
    @Video
    public static void moveVideoAndLogs() throws InterruptedException, FindFailed, IOException {
        WebDriver dummiDriver = null;
        setup("moveVideoAndLogs");
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://172.21.7.239/gbwebphone/");
        teardown(driver, "moveVideoAndLogs");

    }

    @Test
    @Video
    public static void moveVideoAndLogs2() throws InterruptedException, FindFailed, IOException {
        WebDriver dummiDriver = null;
        setup("moveVideoAndLogs2");
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://172.21.7.239/gbwebphone/");
        teardown(driver, "moveVideoAndLogs2");

    }

    @AfterTest
    public static void afterTest() throws IOException, InterruptedException {
        moveVideo();
    }

    @BeforeSuite
    public static void befreSuite(){
        createFolder();
    }
}
