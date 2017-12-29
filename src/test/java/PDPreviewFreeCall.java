import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class PDPreviewFreeCall {
    static Data data;
    static WebDriver driver;

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public static void pDPreviewFreeCall() throws Exception {
        try {
            Methods.setup(PreviewFree.driver);
            PreviewFree.createData();
            PreviewFree.LoginAD();
            PreviewFree.changeStatusToAvailable();
            Methods.switchToAdTab(PreviewFree.driver);
            Methods.runSqlQuery("pd_5009_3", "94949");
            PreviewFree.processCall();
            Methods.teardown(PreviewFree.driver);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
