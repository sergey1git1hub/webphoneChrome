package autotests.logouts;

import actions.AgentAbstractionLayer;
import autotests.calls.callOnTwoLines.CallOnTwoLinesBaseClass;
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

    public static String testName = "Log out from IE11";
    AgentAbstractionLayer agent = new AgentAbstractionLayer();

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void logOutFromIE() throws Exception {
        setup(testName);
        agent.login("Automation Call From Queue");

        teardown(agent.getDriver(), testName);

    }

}
