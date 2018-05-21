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
public class CallFromQueue {
    static Data data;
    static WebDriver driver;
    static boolean fast = false;
    static int delay = 2;
    public static String testName = "Receive call from queue";

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void callFromQueue() throws Exception {
        setup(driver, testName);
        try {
            CallOnTwoLines.login("Automation Call From Queue");
            driver = CallOnTwoLines.driver;
            data = CallOnTwoLines.data;
            Thread.sleep(2000);

            Methods.callToQueue();
            Methods.agentAcceptCall(driver, 30, false);
            Thread.sleep(4000);

            Methods.agentHangup(driver, 1);
            Thread.sleep(1000);
            CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally{
            teardown(driver, testName);
        }

    }

}
