package transfer_supervisor_and_queue; /**
 * Created by SChubuk on 15.11.2017.
 */

import callsMethods.Methods;
import callsMethods.STMethods;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.Flags;
import utils.RetryAnalyzer;

import java.io.IOException;

import static utils.TestSetup.setup;


/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class Transfer {
    static WebDriver transferInitiator;
    static WebDriver transferReceiver;
    static String callToNumber = "94949";
    static String transferToNumber = "94948";
    static String transferToAgent = "81058";
    static String transferFromAgent = "81016";
    static WebDriver dummiDriver;

    static String transferToQueue = "33333";

    static boolean fast = false;
    static int delay = 2;


    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void blindTransferToNumber() throws Exception {
        try {
            setup(dummiDriver);
            transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
            STMethods.call(transferInitiator, callToNumber);
            STMethods.makeTransfer(transferInitiator, "blind", transferToNumber);
            Methods.cxAnswer();
            STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
            Methods.focusCXphone(1);
            Thread.sleep(5000);
            Methods.clientHangup(transferInitiator, 1);
            utils.TestTeardown.teardown(transferInitiator, "blindTransferToNumber");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void attendedTransferToNumber() throws Exception {
        try {
            setup(dummiDriver);
            transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
            STMethods.call(transferInitiator, callToNumber);
            STMethods.makeTransfer(transferInitiator, "attended", transferToNumber);
            Methods.cxAnswer();
            Methods.agentHangup(transferInitiator, 1);
            STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
            Methods.focusCXphone(1);
            Thread.sleep(5000);
            Methods.clientHangup(transferInitiator, 1);
            utils.TestTeardown.teardown(transferInitiator, "attendedTransferToNumber");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void blindTransferToAgent() throws Exception {
        try {
            setup(dummiDriver);
            transferReceiver = STMethods.loginReceiver(transferReceiver, transferToAgent);
            transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
            STMethods.call(transferInitiator, callToNumber);
            STMethods.makeTransfer(transferInitiator, "blind", transferToAgent);
            STMethods.switchWindow();
            STMethods.acceptTransfer(transferReceiver);
            STMethods.switchWindow();
            STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
            STMethods.switchWindow();
            Thread.sleep(5000);
            Methods.agentHangup(transferReceiver, 1);
            STMethods.setResultCodeAndCheckAvailableStatus(transferReceiver);
            utils.TestTeardown.teardown(transferInitiator, "blindTransferToAgent");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void attendedTransferToAgent() throws Exception {
        try {
            setup(dummiDriver);
            transferReceiver = STMethods.loginReceiver(transferReceiver, transferToAgent);
            transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
            STMethods.call(transferInitiator, callToNumber);
            STMethods.makeTransfer(transferInitiator, "attended", transferToAgent);
            STMethods.switchWindow();
            STMethods.acceptTransfer(transferReceiver);
            STMethods.switchWindow();
            Thread.sleep(5000);
            Methods.agentHangup(transferInitiator, 1);
            STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
            Thread.sleep(5000);
            STMethods.switchWindow();
            Methods.agentHangup(transferReceiver, 1);
            STMethods.setResultCodeAndCheckAvailableStatus(transferReceiver);
            utils.TestTeardown.teardown(transferInitiator, "attendedTransferToAgent");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

/*    //call to queue 33333
    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void blindTransferToQueue() throws InterruptedException, IOException, FindFailed {
        transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "blind", transferToQueue);
        STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
        Thread.sleep(10000);
        transferReceiver = transferInitiator;
        STMethods.acceptTransfer(transferReceiver);
        Thread.sleep(5000);
        Methods.clientHangup(transferReceiver, 1);
        STMethods.setResultCodeAndCheckAvailableStatus(transferReceiver);
    }

    //ATTENDED TRANSFER TO QUEUE SHOULD BE THE SAME AS TRANSFER TO AGENT EXCEPT 33333
    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void attendedTransferToQueue() throws InterruptedException, IOException, FindFailed {
        STMethods.login(transferReceiver);
        STMethods.login(transferInitiator);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "attended", transferToAgent);
        STMethods.switchWindow();
        STMethods.acceptTransfer(transferReceiver);
        Thread.sleep(5000);
        Methods.agentHangup(transferInitiator,1);
        STMethods.switchWindow();
        Thread.sleep(5000);
        Methods.agentHangup(transferReceiver,1);
        STMethods.setResultCodeAndCheckAvailableStatus(transferReceiver);
    }*/


    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void blindTransferToPoint() throws Exception {
        try {
            setup(dummiDriver);
            transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
            STMethods.call(transferInitiator, callToNumber);
            STMethods.makeTransfer(transferInitiator, "point", transferToNumber);
            Methods.cxAnswer();
            STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
            Methods.focusCXphone(1);
            Thread.sleep(5000);
            Methods.clientHangup(transferInitiator, 1);
            utils.TestTeardown.teardown(transferInitiator, "blindTransferToPoint");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        }

        //Threre is no guarantee of transfer to othrer agent, not to yourself

        // @Test

    public static void attendedTransferToPoint() throws InterruptedException, IOException, FindFailed {


    }

   /* @BeforeMethod
    public void open() throws InterruptedException, FindFailed, IOException {
        Methods.openCXphone(100);
        //before groups to launch ie browser
    }*/

    //alailability schedule for transfer point - not really needed
    @AfterMethod
    public static void teardown() throws IOException {
        try {
            transferInitiator.quit();
            transferReceiver.quit();
            boolean isIE = Flags.isIE(transferInitiator) || Flags.isIE(transferReceiver);

            if (isIE) {
                Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
    }


}


// time on each test 2h