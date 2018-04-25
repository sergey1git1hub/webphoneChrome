package uiLayer.login;

import configs.BrowserFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
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

        WebDriverWait waitForTitle = new WebDriverWait(driver, 10);
        waitForTitle.until(ExpectedConditions.titleIs("gbwebphone"));
        Assert.assertEquals(driver.getTitle(), "gbwebphone");

    }

    public void changeLanguage(WebDriver driver, String language){
        By buttonConnectLable = By.cssSelector("#btn_connect > span.ui-button-text.ui-c");
        WebElement buttonConnectLableElement = driver.findElement(buttonConnectLable);
        if(buttonConnectLableElement.getText().equalsIgnoreCase("connect")){
            return;
        }

        WebElement languageDropdown = driver.findElement(By.cssSelector("#lang_input_label"));
        languageDropdown.click();

        By languageSelector = By.xpath("//li[text() = '" + language + "']");

        WebElement language_en = driver.findElement(languageSelector);
        language_en.click();
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

        System.out.println("Breakpoint for debug.");

        //Verification point
        By button_BackSelector = By.cssSelector("[name='btn_power_group']");
        WebDriverWait waitForButton_Back = new WebDriverWait(driver, 3);
        waitForButton_Back.until(ExpectedConditions.elementToBeClickable(button_BackSelector));
    }

    private void ssoLogin() {
        //not refactored yet
    }


}
