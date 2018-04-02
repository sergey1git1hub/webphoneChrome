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

import static utils.Flags.isIE;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class IncorrectLogoutCloseBrowser extends IncorrectLogout {

    public static String testName = "Incorrect logout when browser window closed";
    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void incorrectLogoutCloseBrowser() throws Exception {
        setup(driver, testName);
        IncorrectLogoutCloseBrowser incorrectLogoutCloseBrowser = new IncorrectLogoutCloseBrowser();
        incorrectLogoutCloseBrowser.incorrectLogout();
        teardown(driver, testName);

    }

    @Override
    public void logoutHook() throws FindFailed, InterruptedException {

        if (isIE(driver)) {
            Screen screen = new Screen();
            org.sikuli.script.Pattern closeIEWindow = new org.sikuli.script.Pattern("C:\\SikuliImages\\closeIEWindow.png");
            screen.wait(closeIEWindow, 2);
            screen.click(closeIEWindow);

        } else {
            Screen screen = new Screen();
            org.sikuli.script.Pattern closeChromeWindow1 = new org.sikuli.script.Pattern("C:\\SikuliImages\\closeChromeWindow1.png");
            screen.wait(closeChromeWindow1, 2);
            screen.click(closeChromeWindow1);
        }

//closeIEWindow.png
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
    }

}
