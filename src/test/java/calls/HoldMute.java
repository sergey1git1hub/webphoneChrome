package calls;

import callsMethods.CallOnTwoLines;
import callsMethods.Methods;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class HoldMute {
    static Data data;
    static WebDriver driver;
    static boolean fast = false;
    static int delay = 2;
    public static String testName = "Check Hold and Mute functionality";

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void holdMute() throws Exception {
        try {
            setup(driver, testName);
            CallOnTwoLines.call();
            driver = CallOnTwoLines.driver;
            data = CallOnTwoLines.data;

            //hold, unhold
            Methods.hold(driver);
            Thread.sleep(4000);
            Methods.unhold(driver);

            Thread.sleep(4000);

            //mute, unmute
            Methods.mute(driver);
            Thread.sleep(4000);
            Methods.unmute(driver);


            Methods.clientHangup(driver, 1);
            CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        } catch (Exception e) {
            e.printStackTrace();
            if(driver == null){
                driver = CallOnTwoLines.driver;
            }
            throw e;
        } finally{
            teardown(driver, testName);
        }


    }

}
