package callsMethods;

import data.Data;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import static data.Data.createData;
import static utils.Flags.isLocal;

/**
 * Created by SChubuk on 04.10.2017.
 */
public class PreviewFree {

    public static Data data;
    public static WebDriver driver;

    public static void createTestData() throws UnknownHostException {
        data = createData();
        if (isLocal()) {
            data.group = "Automation Preview Free Local";
        } else {
            data.group = "Automation Preview Free Jenkins";
        }

        Methods.browser = data.browser;
        Methods.onJenkins = false;
    }

    public static void LoginAD() throws InterruptedException, IOException, FindFailed {
        driver = Methods.openWebphoneLoginPage(driver, data.webphoneUrl);
        Methods.login(driver, data.method, data.username, data.group);
        Methods.checkStatus(driver, "Тренинг", 60);
    }

    public static void changeStatusToAvailable() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.changeStatus(driver, "Available");
        Methods.checkStatus(driver, "Available", 3);

    }

    public static void changeStatusToAUX() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.changeStatus(driver, "AUX");
        Methods.checkStatus(driver, "AUX", 3);
    }


    public static void processCall() throws InterruptedException, FindFailed, IOException {
        try {
            // Methods.openCXphone(5000);
            Methods.agentAcceptCall(driver, 40, true);
        } catch (Exception e) {
            e.printStackTrace();
            WebDriver driverTemp = Methods.loginToPD();
            if (isLocal()) {
                Methods.runPDCampaign(driverTemp, 252);
            } else {
                Methods.runPDCampaign(driverTemp, 280);
            }
            Methods.agentAcceptCall(driver, 30, true);
        }
        Methods.cxAnswer();
        Methods.saveCRMCard(driver);
        Methods.checkStatus(driver, "Relax", 3);
        Methods.checkStatus(driver, "Available", 6);
    }

}
