package calls;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import callsMethods.Methods;
import callsMethods.PreviewFree;
import utils.RetryAnalyzer;

import static utils.Flags.isLocal;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class PDPreviewFreeCall {
    static Data data;
    static WebDriver driver;
    public static String testName = "Agent receives preview free call from Powerdialer";

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void pDPreviewFreeCall() throws Exception {
        try {
            setup(PreviewFree.driver, testName);
            PreviewFree.createTestData();
            PreviewFree.LoginAD();
            PreviewFree.changeStatusToAvailable();
            Methods.switchToAdTab(PreviewFree.driver);
            if (isLocal()) {
                Methods.runSqlQuery("pd_automation_preview_free_local", "94949");
            } else {
                Methods.runSqlQuery("pd_automation_preview_free_jenkins", "94944");
            }
            PreviewFree.processCall();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            teardown(PreviewFree.driver, testName);
        }

    }

}
