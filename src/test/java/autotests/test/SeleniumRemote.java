package autotests.test;

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
public class SeleniumRemote {

    public static String testName = "Selenium Remote";
    AgentAbstractionLayer agent = new AgentAbstractionLayer();
    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void seleniumRemote() throws Exception {

        setup(testName);
        agent.login("Automation Call From Queue");
        Thread.sleep(5000);
        teardown(agent.getDriver(), testName);
    }

}
