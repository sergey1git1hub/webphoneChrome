package callsMethods;

import data.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import static data.Data.createData;

/**
 * Created by SChubuk on 04.10.2017.
 */
public class PreviewFree {

    public static Data data;
    public static WebDriver driver;

    public static void createTestData() throws UnknownHostException {
        data = createData();
        data.group = "pasha_G_5_copy_preview";
        Methods.browser = data.browser;
        Methods.onJenkins = false;
    }

    public static void LoginAD() throws InterruptedException, IOException, FindFailed {
        driver = Methods.openWebphoneLoginPage(driver, data.browser, data.webphoneUrl);
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
        /*try{*/
       // Methods.openCXphone(5000);
        Methods.agentAcceptCall(driver, 40000, true);
       /* } catch(Exception e){
            e.printStackTrace();
            WebDriver driverTemp = Methods.loginToPD();
            Methods.runPDCampaign(driverTemp, 252);
            Methods.agentAcceptCall(driver, 30);
        }*/
        Methods.cxAnswer();
        Methods.saveCRMCard(driver);
        Methods.checkStatus(driver, "Relax", 3);
        Methods.checkStatus(driver, "Available", 6);
    }

}
