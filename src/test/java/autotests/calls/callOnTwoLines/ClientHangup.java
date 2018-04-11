package autotests.calls.callOnTwoLines;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.testng.annotations.*;
import utils.RetryAnalyzer;

import static actions.client.Client.hangup;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */

@Listeners(VideoListener.class)
public class ClientHangup extends CallOnTwoLinesBaseClass{

    public static String testName = "Call on two lines with hangup on client side";

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public  void clientHangup() throws Exception {

        setup(testName);
        ClientHangup clientHangup = new ClientHangup();
        clientHangup.callOnTwoLines();
        teardown(agent.getDriver(), testName);

    }

    @Override
    public  void hangupHook() throws Exception {
        hangup(1);
        Thread.sleep(2000);
        hangup(2);
        Thread.sleep(2000);
    }
}


