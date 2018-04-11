package autotests.logouts;

import actions.AgentAbstractionLayer;

import com.automation.remarks.testng.VideoListener;

import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static actions.database.Webphone.isLogoutRecordPresent;


/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class IncorrectLogoutBaseClass {
    AgentAbstractionLayer agent = new AgentAbstractionLayer();


    public void incorrectLogout() throws Exception {
        agent.login();
        Thread.sleep(2000);
        Date dateBeforeLogout = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String stringDateBeforeLogout = df.format(dateBeforeLogout);
        logoutHook();
        Thread.sleep(1000);
        Assert.assertTrue(isLogoutRecordPresent(stringDateBeforeLogout, agent.getData().username,1,120));
        Thread.sleep(2000);

    }

    public void logoutHook() throws FindFailed, InterruptedException, IOException {

    }

}
