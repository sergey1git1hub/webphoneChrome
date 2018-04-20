package uiLayer.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Waiter;

import java.io.IOException;

/**
 * Created by SChubuk on 19.04.2018.
 */

public class WebphoneLoginPage {

    private static final String passwordValue = "1"; //newer changes

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

    private void ssoLogin(){
        //not refactored yet
    }
}
