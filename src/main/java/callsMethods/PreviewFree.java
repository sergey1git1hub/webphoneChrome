package callsMethods;

import data.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import static actions.agentdesktopTab.AgentdesktopTab.saveCRMCard;
import static actions.client.Client.cxAnswer;
import static actions.login.Login.login;
import static actions.login.Login.openWebphoneLoginPage;
import static actions.powerdialer.Powerdialer.loginToPD;
import static actions.powerdialer.Powerdialer.runPDCampaign;
import static actions.webphonePanel.WebphonePanel.agentAcceptCall;
import static actions.webphonePanel.WebphonePanel.changeStatus;
import static actions.webphonePanel.WebphonePanel.checkStatus;
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
        driver = openWebphoneLoginPage(driver, data.browser, data.webphoneUrl);
        login(driver, data.method, data.username, data.group);
        checkStatus(driver, "Тренинг", 60);
    }

    public static void changeStatusToAvailable() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        changeStatus(driver, "Available");
        checkStatus(driver, "Available", 3);

    }

    public static void changeStatusToAUX() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        changeStatus(driver, "AUX");
        checkStatus(driver, "AUX", 3);
    }


    public static void processCall() throws InterruptedException, FindFailed, IOException {
        try {
            // Methods.openCXphone(5000);
            agentAcceptCall(driver, 40, true);
        } catch (Exception e) {
            e.printStackTrace();
            WebDriver driverTemp = loginToPD();
            if (isLocal()) {
                runPDCampaign(driverTemp, 252);
            } else {
                runPDCampaign(driverTemp, 280);
            }
            agentAcceptCall(driver, 30, true);
        }
        cxAnswer();
        saveCRMCard(driver);
        checkStatus(driver, "Relax", 3);
        checkStatus(driver, "Available", 6);
    }

}
