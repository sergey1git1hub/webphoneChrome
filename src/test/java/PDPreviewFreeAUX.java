import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.SQLException;

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
            Methods.setup(driver);
            PreviewFree.createData();
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
            Methods.teardown(driver);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
