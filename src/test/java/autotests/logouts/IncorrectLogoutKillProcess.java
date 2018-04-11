package autotests.logouts;

import actions.AgentAbstractionLayer;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import java.io.IOException;

import static utils.Flags.isIE;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class IncorrectLogoutKillProcess extends IncorrectLogoutBaseClass {
    public static String testName = "Incorrect logout when browser process killed";
    AgentAbstractionLayer agent = new AgentAbstractionLayer();
    WebDriver driver = agent.getDriver();

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void incorrectLogoutKillProcess() throws Exception {
        setup(testName);
        IncorrectLogoutKillProcess incorrectLogoutKillProcess = new IncorrectLogoutKillProcess();
        incorrectLogoutKillProcess.incorrectLogout();
        teardown(driver, testName);
    }

    public void logoutHook() throws IOException, InterruptedException {
        if (isIE(driver)) {
            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
        } else {
            Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
        }
    }

}
