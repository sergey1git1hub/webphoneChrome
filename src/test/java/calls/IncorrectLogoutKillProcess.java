package calls;

import callsMethods.CallOnTwoLines;
import callsMethods.Methods;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.Flags.isIE;
import static utils.TestSetup.killBrowser;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class IncorrectLogoutKillProcess extends IncorrectLogout {
    public static String testName = "Incorrect logout when browser process killed";

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void incorrectLogoutKillProcess() throws Exception {
        setup(driver, testName);
        IncorrectLogoutKillProcess incorrectLogoutKillProcess = new IncorrectLogoutKillProcess();
        incorrectLogoutKillProcess.incorrectLogout();
        teardown(driver, testName);
    }

    public void logoutHook() throws Exception {
        killBrowser();
    }

}
