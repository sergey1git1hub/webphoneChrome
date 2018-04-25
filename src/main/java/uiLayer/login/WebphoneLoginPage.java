package uiLayer.login;

import configs.BrowserFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.Waiter;

import java.io.IOException;


/**
 * Created by SChubuk on 19.04.2018.
 */

public class WebphoneLoginPage {

    private static final String passwordValue = "1"; //newer changes
    private String webphoneVersion = System.getProperty("webphoneVersion");
    private String webphone1Url = System.getProperty("webphone1Url");
    private String webphone2Url = System.getProperty("webphone2Url");

    public void openWebphone(WebDriver driver) {
        driver.manage().window().maximize();
        if (webphoneVersion.equalsIgnoreCase("1")) {
            driver.get(webphone1Url);
        }
        if (webphoneVersion.equalsIgnoreCase("2")) {
            driver.get(webphone2Url);
        }

    }

    public void login(WebDriver driver, String usernameValue) throws InterruptedException, IOException {
        Waiter waiter = new Waiter();
        By byNameU = By.cssSelector("[name=username_input]");
        WebDriverWait waitForUsername = new WebDriverWait(driver, 5);
        waitForUsername.until(ExpectedConditions.presenceOfElementLocated(byNameU));
        WebElement userName = driver.findElement(byNameU);
        userName.clear();
        userName.sendKeys(usernameValue);

        waiter.wait(500);

        By byNameP = By.cssSelector("[name=password_input]");
        WebElement password = driver.findElement(byNameP);
        password.clear();
        password.sendKeys(passwordValue);

        waiter.wait(1000);

        WebElement button_Connect = driver.findElement(By.cssSelector("[name='btn_connect']"));
        button_Connect.click();

    }

    private void ssoLogin() {
        //not refactored yet
    }

    @Test
    private void testWebphoneLoginPage() throws Exception {
        BrowserFactory browserFactory = new BrowserFactory();
        WebDriver driver = browserFactory.getBrowser(false);
        WebphoneLoginPage webphoneLoginPage = new WebphoneLoginPage();

        webphoneLoginPage.openWebphone(driver);
        webphoneLoginPage.login(driver, "81016");
    }
}
