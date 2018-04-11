package autotests.calls;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import callsMethods.Methods;
import callsMethods.PreviewFree;
import utils.RetryAnalyzer;

import static actions.agentdesktopTab.AgentdesktopTab.switchToAdTab;
import static actions.database.Powerdialer.runSqlQuery;
import static utils.Flags.isLocal;
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
    public static String testName = "Agent cannot receive preview free call from Powerdialer while in AUX";

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void pDPreviewFreeAUX() throws Exception {
        try {
            setup(testName);
            PreviewFree.createTestData();
            PreviewFree.LoginAD();
            switchToAdTab(PreviewFree.driver);
            PreviewFree.changeStatusToAUX();
            if (isLocal()) {
                runSqlQuery("pd_automation_preview_free_local", "94948");
            } else {
                runSqlQuery("pd_automation_preview_free_jenkins", "94944");
            }
            if (debug == true)
                Thread.sleep(5000);
            else Thread.sleep(20000);
            PreviewFree.changeStatusToAvailable();
            //no incoming call
            PreviewFree.processCall();
            teardown(PreviewFree.driver, testName);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
