package autotests.calls;

import actions.AgentAbstractionLayer;
import autotests.calls.callOnTwoLines.CallOnTwoLinesBaseClass;
import callsMethods.Methods;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import static actions.webphonePanel.WebphonePanel.agentAcceptCall;
import static actions.webphonePanel.WebphonePanel.agentHangup;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;


@Listeners(VideoListener.class)
public class CallFromQueue {
    AgentAbstractionLayer agent = new AgentAbstractionLayer();
    public static String testName = "Receive call from queue";
    public static WebDriver driver;

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void callFromQueue() throws Exception {
        driver = agent.getDriver();

        setup(testName);
        agent.login("Automation Call From Queue");
        Thread.sleep(2000);

        Methods.callToQueue();
        agentAcceptCall(driver, 30, false);
        Thread.sleep(4000);

        agentHangup(driver, 1);
        Thread.sleep(1000);
        agent.setResultCodeAndCheckAvailableStatus();
        teardown(driver, testName);

    }

}
