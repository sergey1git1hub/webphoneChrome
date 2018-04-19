package actions.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static callsMethods.Methods.executeJavaScriptOrClick;
import static callsMethods.Methods.log;
import static utils.Flags.isIE;

/**
 * Created by SChubuk on 11.04.2018.
 */
public class Logout {

    public static void logOut(WebDriver driver) throws InterruptedException {
        if( driver.findElement(By.cssSelector("#btn_connect")).isDisplayed()){
            return;
        }
        WebElement button_LogOut = driver.findElement(By.cssSelector("#btn_power"));
        if (isIE(driver)) {
            try {
                executeJavaScriptOrClick(driver, button_LogOut, "arguments[0].click();");
                try {
                    WebDriverWait waitForAlert = new WebDriverWait(driver, 12);
                    waitForAlert.until(ExpectedConditions.alertIsPresent());
                    driver.switchTo().alert().accept();
                    Thread.sleep(500);
                    driver.navigate().refresh();
                } catch (Exception e) {
                    log("There is no logout alert in IE11.", "DEBUG");
                }
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#btn_connect")));
                log("Log out.", "INFO");
            } catch (Exception e) {
                log("Logout failed in IE11.", "INFO");
            }

        } else {
            button_LogOut.click();
            log("Log out.", "INFO");
        }
    }

}
