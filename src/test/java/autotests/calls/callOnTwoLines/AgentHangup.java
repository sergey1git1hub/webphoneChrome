package autotests.calls.callOnTwoLines;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.RetryAnalyzer;

import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;


/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class AgentHangup extends CallOnTwoLinesBaseClass {

    public static String testName = "Call on two lines with hangup on agent side";
    WebDriver driver = agent.getDriver();

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public  void agentHangup(ITestContext ctx) throws Exception {
        setup(testName);
        AgentHangup agentHangup = new AgentHangup();
        agentHangup.callOnTwoLines();
        teardown(driver, testName);
    }

    @Override
    public  void hangupHook() throws Exception {
        Thread.sleep(1000);
        WebElement button_Hold = driver.findElement(By.cssSelector("#btn_hold"));
        button_Hold.click();
        Thread.sleep(1000);
        actions.webphonePanel.WebphonePanel.agentHangup(driver, 1);
        Thread.sleep(1000);
        actions.webphonePanel.WebphonePanel.agentHangup(driver, 2);
        Thread.sleep(1000);
    }

}

