package calls;

import callsMethods.CallOnTwoLines;
import callsMethods.Methods;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class IncorrectLogoutCloseBrowser extends IncorrectLogout{

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void incorrectLogoutCloseBrowser() throws Exception {
        IncorrectLogoutCloseBrowser incorrectLogoutCloseBrowser = new  IncorrectLogoutCloseBrowser();
        incorrectLogoutCloseBrowser.incorrectLogout();

    }

    @Override
    public void logoutHook() throws FindFailed, InterruptedException {
        Screen screen = new Screen();
        org.sikuli.script.Pattern closeChromeWindow1 = new org.sikuli.script.Pattern("C:\\SikuliImages\\closeChromeWindow1.png");
        screen.wait(closeChromeWindow1, 2);
        screen.click(closeChromeWindow1);


        Thread.sleep(2000);
        driver.switchTo().alert().accept();
    }

}