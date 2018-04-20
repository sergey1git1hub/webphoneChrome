package uiLayer.login;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Waiter;

import static java.lang.Thread.sleep;

/**
 * Created by SChubuk on 19.04.2018.
 */
public class SelectGroupPage {

    Waiter waiter = new Waiter();

    public void selectGroup(WebDriver driver, String group) {
        WebDriverWait waitForButtonContinue = new WebDriverWait(driver, 10);
        waitForButtonContinue.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#btn_continue > span.ui-button-text.ui-c")));
        WebDriverWait waitForGroupList = new WebDriverWait(driver, 10);
        waitForGroupList.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#group_input_label")));
        WebElement groupList = driver.findElement(By.cssSelector("#group_input_label"));
        groupList.click();

        By groupSelector = By.cssSelector("[data-label='" + group + "']");

        WebElement element = driver.findElement(groupSelector);
        //do not remove JavascriptExecutor here
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement groupInDropdown = driver.findElement(By.cssSelector("[data-label='" + group + "']"));
        groupInDropdown.click();

        waiter.wait(1000);

        WebElement btnContinue = driver.findElement(By.cssSelector("#btn_continue > span.ui-button-text.ui-c"));
        btnContinue.click();


    }
}
