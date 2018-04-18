package calls;

import callsMethods.CallOnTwoLines;
import callsMethods.Methods;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
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
public class IncorrectLogout {
    static Data data;
    static WebDriver driver;
    static boolean fast = false;
    static int delay = 2;


    public void incorrectLogout() throws Exception {
        CallOnTwoLines.login();
        driver = CallOnTwoLines.driver;
        data = CallOnTwoLines.data;

        Thread.sleep(2000);

        Date dateBeforeLogout = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String stringDateBeforeLogout = df.format(dateBeforeLogout);

        logoutHook();

        Thread.sleep(1000);

        Assert.assertTrue(Methods.isLogoutRecordPresent(stringDateBeforeLogout, data.username,1,120));

        CallOnTwoLines.login();
        driver = CallOnTwoLines.driver;

        Thread.sleep(2000);



    }

    public void logoutHook() throws Exception {

    }

}
