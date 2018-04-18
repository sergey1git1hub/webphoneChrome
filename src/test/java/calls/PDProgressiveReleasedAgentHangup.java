package calls;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import callsMethods.Methods;
import utils.RetryAnalyzer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import static callsMethods.Methods.log;
import static data.Data.createData;
import static utils.Flags.isLocal;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)

public class PDProgressiveReleasedAgentHangup {
    static Data data;
    static WebDriver driver;
    static boolean debug = true;
    public static String testName = "Agent receives progressive released call from Powerdialer";

    public static void IELogin() throws InterruptedException, IOException, FindFailed {
        data = createData();
        if (isLocal()) {
            data.group = "Automation Progressive Released Local";
        } else {
            data.group = "Automation Progressive Released Jenkins";
        }
        Methods.browser = data.browser;
        driver = Methods.openWebphoneLoginPage(driver, data.webphoneUrl);
        Methods.login(driver, data.method, data.username, data.group);
        Methods.checkStatus(driver, "Available", 10);
    }

    public static void changeStatusToAUX() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.changeStatus(driver, "AUX");
    }


    public static void runSQLQuery() throws SQLException, ClassNotFoundException, InterruptedException, FindFailed, IOException {
        if (isLocal()) {
            Methods.runSqlQuery("pd_automation_progressive_released_local", "94949");
        } else {
            Methods.runSqlQuery("pd_automation_progressive_released_jenkins", "94944");
        }

        Methods.openCXphone(2000);
    }


    public static void noIncomingCallToClient() throws InterruptedException {
        if (debug == true)
            Thread.sleep(5000);
        else Thread.sleep(20000);
        log("Check that there is no incoming call to client", "DEBUG");
    }

    public static void changeStatusToAvailable() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.changeStatus(driver, "Available");
    }


    public static void answerCallOnClientSide() throws FindFailed, InterruptedException, UnknownHostException, UnsupportedEncodingException {
        Methods.cxAnswer();
        Methods.checkStatus(driver, "Incall", 5);
    }

    public static void answerCallOnClientSide(int waitTime) throws FindFailed, InterruptedException, UnknownHostException, UnsupportedEncodingException {
        Methods.cxAnswer(waitTime);
        Methods.checkStatus(driver, "Incall", 5);
    }

    public static void agentHangup() throws InterruptedException, FindFailed, UnknownHostException {
        Thread.sleep(2000);
        Methods.agentHangup(driver, 1);
    }


    public static void setResultCodeAndCheckAvailableStatus() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.setWebphoneResultCode(driver);
        Methods.checkStatus(driver, "Available", 3);

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void pDProgressiveReleasedAgentHangup() throws Exception {
        try {
            setup(driver, testName);
            IELogin();
            changeStatusToAUX();
            runSQLQuery();
            noIncomingCallToClient();
            changeStatusToAvailable();
            answerCallOnClientSide(40);
            agentHangup();
            setResultCodeAndCheckAvailableStatus();
            teardown(driver, testName);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
