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

import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class IncorrectLogoutKillProcess  extends IncorrectLogout{

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void incorrectLogoutKillProcess() throws Exception {
        IncorrectLogoutKillProcess incorrectLogoutKillProcess = new  IncorrectLogoutKillProcess();
        incorrectLogoutKillProcess.incorrectLogout();
    }

    public void logoutHook() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
    }

}
