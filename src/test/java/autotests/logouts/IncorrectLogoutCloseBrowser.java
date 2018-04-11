package autotests.logouts;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import static utils.Flags.isIE;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class IncorrectLogoutCloseBrowser extends IncorrectLogoutBaseClass {

    public static String testName = "Incorrect logout when browser window closed";
    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void incorrectLogoutCloseBrowser() throws Exception {
        setup(testName);
        IncorrectLogoutCloseBrowser incorrectLogoutCloseBrowser = new IncorrectLogoutCloseBrowser();
        incorrectLogoutCloseBrowser.incorrectLogout();
        teardown(agent.getDriver(), testName);

    }

    @Override
    public void logoutHook() throws FindFailed, InterruptedException {

        if (isIE(agent.getDriver())) {
            Screen screen = new Screen();
            Pattern closeIEWindow = new Pattern("C:\\SikuliImages\\closeIEWindow.png");
            screen.wait(closeIEWindow, 2);
            screen.click(closeIEWindow);

        } else {
            Screen screen = new Screen();
            Pattern closeChromeWindow1 = new Pattern("C:\\SikuliImages\\closeChromeWindow1.png");
            screen.wait(closeChromeWindow1, 2);
            screen.click(closeChromeWindow1);
        }

//closeIEWindow.png
        Thread.sleep(2000);
        agent.getDriver().switchTo().alert().accept();
    }

}
