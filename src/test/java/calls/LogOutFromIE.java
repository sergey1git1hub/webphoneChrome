package calls;

import callsMethods.CallOnTwoLines;
import callsMethods.Methods;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;


@Listeners(VideoListener.class)
public class LogOutFromIE {
    static Data data;
    static WebDriver driver;
    static boolean fast = false;
    static int delay = 2;
    public static String testName = "Log out from IE11";

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void logOutFromIE() throws Exception {
        setup(driver, testName);
        CallOnTwoLines.login("Automation Call From Queue");
        driver = CallOnTwoLines.driver;
        data = CallOnTwoLines.data;

        teardown(driver, testName);

    }

}
