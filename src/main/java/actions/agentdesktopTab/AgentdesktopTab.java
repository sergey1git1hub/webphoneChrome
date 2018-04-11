package actions.agentdesktopTab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import java.util.regex.Pattern;

import static callsMethods.Methods.log;
import static utils.Flags.isChrome;

/**
 * Created by SChubuk on 11.04.2018.
 */
public class AgentdesktopTab {
    public static WebDriver saveCRMCard(WebDriver driver) throws FindFailed, InterruptedException {
        System.out.println("saveCRMCard");
        WebDriverWait waitForIncallStatus = new WebDriverWait(driver, 5);
        waitForIncallStatus.until(ExpectedConditions.textMatches(By.cssSelector(
                "#statusButton > span.ui-button-text.ui-c"), Pattern.compile(".*\\bIncall\\b.*")));
        Screen screen = new Screen();

        org.sikuli.script.Pattern mltest;
        if (isChrome(driver)) {
            mltest = new org.sikuli.script.Pattern("C:\\SikuliImages\\mltestChrome.png");
        } else {
            mltest = new org.sikuli.script.Pattern("C:\\SikuliImages\\mltest.png");
        }
        screen.wait(mltest, 10);
        screen.click(mltest);

        org.sikuli.script.Pattern button_OK = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_OK.png");
        screen.wait(button_OK, 10);
        screen.click(button_OK);

        log("Select MLTest card", "INFO");

        driver.switchTo().frame("TAB_123");
        try {
            WebElement visitDate = driver.findElement(By.cssSelector("[name = 'cardValues[0].value']"));
            if (visitDate.getText().equalsIgnoreCase("")) {
                visitDate.sendKeys("2018-03-20");
                log("Fill visit date", "INFO");
            }
        } catch (Exception e) {
            e.getMessage();
            log("Visit date not changed", "INFO");
        }


        //visitDate.click();

        driver.switchTo().defaultContent();

        org.sikuli.script.Pattern button_nextForm = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_nextForm.png");
        screen.wait(button_nextForm, 10);
        screen.click(button_nextForm);

        org.sikuli.script.Pattern button_save = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_save.png");
        screen.wait(button_save, 10);
        screen.click(button_save);

        log("Click button \"next\" to open select result code window.", "INFO");
        org.sikuli.script.Pattern selectResultCode = new org.sikuli.script.Pattern("C:\\SikuliImages\\selectResultCode.png");
        screen.wait(selectResultCode, 10);
        screen.click(selectResultCode);

        org.sikuli.script.Pattern callLater = new org.sikuli.script.Pattern("C:\\SikuliImages\\callLater.png");
        screen.wait(callLater, 10);
        screen.click(callLater);

        log("Select result code \"Call later\".", "INFO");
        Thread.sleep(2000);
        screen.wait(button_save, 10);
        screen.click(button_save);
        log("Save CRM card.", "INFO");

        return driver;
    }


    public static WebDriver switchToAdTab(WebDriver driver) {
        WebElement adTab = driver.findElement(By.xpath("//a[@href = '#tabView:tab123']"));
        adTab.click();
        try {

            driver.switchTo().frame("TAB_123");
            WebElement username = driver.findElement(By.cssSelector("[name = 'j_username']"));
            username.sendKeys("81016");
            WebElement password = driver.findElement(By.cssSelector("[name = 'j_password']"));
            password.sendKeys("1");
            WebElement login = driver.findElement(By.cssSelector("[name = 'login']"));
            login.click();
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log("Switch to Agentdestop tab.", "DEBUG");
        return driver;
    }

}
