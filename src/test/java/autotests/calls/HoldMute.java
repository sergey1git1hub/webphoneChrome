package autotests.calls;

import actions.AgentAbstractionLayer;
import autotests.calls.callOnTwoLines.CallOnTwoLinesBaseClass;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import static actions.client.Client.hangup;
import static actions.webphonePanel.WebphonePanel.*;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class HoldMute {
    AgentAbstractionLayer agent = new AgentAbstractionLayer();
    public static String testName = "Check Hold and Mute functionality";
    WebDriver driver = agent.getDriver();

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public  void holdMute() throws Exception {

        setup(testName);
        agent.call();


        //hold, unhold
        hold(driver);
        Thread.sleep(4000);
        unhold(driver);

        Thread.sleep(4000);

        //mute, unmute
        mute(driver);
        Thread.sleep(4000);
        unmute(driver);


        hangup(1);
        agent.setResultCodeAndCheckAvailableStatus();
        teardown(driver, testName);


    }

}
