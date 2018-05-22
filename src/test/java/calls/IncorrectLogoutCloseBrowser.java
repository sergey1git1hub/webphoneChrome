package calls;

import callsMethods.CallOnTwoLines;
import callsMethods.Methods;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import data.Data;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static callsMethods.Methods.log;
import static callsMethods.Methods.sikuliClickElement;
import static utils.Flags.isChrome;
import static utils.Flags.isIE;
import static utils.TestSetup.setup;
import static utils.TestTeardown.teardown;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class IncorrectLogoutCloseBrowser extends IncorrectLogout {

    public static String testName = "Incorrect logout when browser window closed";
    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Video
    public void incorrectLogoutCloseBrowser() throws Exception {
        setup(driver, testName);
        IncorrectLogoutCloseBrowser incorrectLogoutCloseBrowser = new IncorrectLogoutCloseBrowser();
        incorrectLogoutCloseBrowser.incorrectLogout();
        teardown(driver, testName);

    }

    @Override
    public void logoutHook() throws Exception {

        if (isIE(driver)){
            sikuliClickAnyElement("closeIEWindowInFocus,closeIEWindowWithoutFocus");
        } else if(isChrome(driver)) {
            sikuliClickAnyElement("closeChromeWindowInFocus,closeChromeWindowWithoutFocus");
        } else{
            sikuliClickAnyElement("closeOperaWindowInFocus,closeOperaWindowWithoutFocus");
        }

//closeIEWindow.png
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
    }

    public void sikuliClickAnyElement(String elementNamesSeparatedByComma) throws Exception {
        int exception = 0;
        String[] elementNames = elementNamesSeparatedByComma.split(",");
        for(String element : elementNames){
            try{
            sikuliClickElement(element, 1);
            } catch (Exception e){
                //e.printStackTrace();
                log("Sikuli: " + elementNamesSeparatedByComma + " not found.", "DEBUG");
                exception++;
            }
        }
        if(exception==2){
            throw new Exception("Sikuli: " + elementNamesSeparatedByComma + " not found.");
        }
    }

}
