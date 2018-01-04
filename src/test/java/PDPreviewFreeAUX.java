import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import testMethods.Methods;
import testMethods.PreviewFree;
import utils.RetryAnalyzer;

import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class PDPreviewFreeAUX {
    static Data data;
    static WebDriver driver;
    static boolean debug = true;

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void pDPreviewFreeAUX() throws Exception {
        try {
            setup(PreviewFree.driver);
            PreviewFree.createTestData();
            PreviewFree.LoginAD();
            Methods.switchToAdTab(PreviewFree.driver);
            PreviewFree.changeStatusToAUX();
            Methods.runSqlQuery("pd_5009_3", "94949");
            if (debug == true)
                Thread.sleep(5000);
            else Thread.sleep(20000);
            PreviewFree.changeStatusToAvailable();
            //no incoming call
            PreviewFree.processCall();
            teardown(PreviewFree.driver, "pDPreviewFreeAUX");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
