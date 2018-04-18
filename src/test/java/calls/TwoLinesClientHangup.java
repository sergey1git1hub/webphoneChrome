package calls;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import callsMethods.CallOnTwoLines;
import callsMethods.Methods;
import utils.RetryAnalyzer;

import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class TwoLinesClientHangup {
    static Data data;
    static WebDriver driver;
    static boolean fast = false;
    static int delay = 2;
    public static String testName = "Call on two lines with hangup on client side";

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void twoLinesClientHangup() throws Exception {
        try {
        setup(driver, testName);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        teardown(driver, testName);

    }
    }


