package actions.powerdialer;

import data.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import static callsMethods.Methods.log;

/**
 * Created by SChubuk on 11.04.2018.
 */
public class Powerdialer {
    public static WebDriver loginToPD() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        WebDriver agentPD = new ChromeDriver();
        agentPD.manage().window().maximize();
        agentPD.get(Data.PDUrl);

        System.out.println(agentPD.getTitle());
        Thread.sleep(3000);
        Assert.assertEquals(agentPD.getTitle(), "gbpowerdialer");

        Thread.sleep(1000);
        WebElement username = agentPD.findElement(By.cssSelector("#username"));
        username.sendKeys("81016");
        WebElement password = agentPD.findElement(By.cssSelector("#password"));
        password.sendKeys("1");
        WebElement button_SignIn = agentPD.findElement(By.cssSelector("#loginModal > div > div > form > div.modal-footer > button"));
        button_SignIn.click();

        log("Login to powerdialer as 81016(password=1)", "INFO");
        return agentPD;
    }

    public static void runPDCampaign(WebDriver agentPD, int campaignId) throws InterruptedException {

        Thread.sleep(2000);

        WebElement columns = agentPD.findElement(By.xpath("//*[@id=\"campaignGrid\"]/div/div[1]"));
        columns.click();
        Thread.sleep(1000);

        WebElement id = agentPD.findElement(By.xpath("//*[@id=\"menuitem-13\"]/button"));
        id.click();
        Thread.sleep(1000);
        WebElement running = agentPD.findElement(By.xpath("//*[text() = '" + campaignId + "']//parent::div//following-sibling::div[3]//div"));


        System.out.println(running.getText());
        if (!running.getText().equals("Running")) {
            WebElement currentCampaign = agentPD.findElement(By.xpath("//*[text() = '" + campaignId + "']"));
            currentCampaign.click();
            WebElement icon_Enable = agentPD.findElement(By.cssSelector("#navbarCompainList > div > div:nth-child(7) > button"));
            icon_Enable.click();
            WebElement button_Enable = agentPD.findElement(By.cssSelector("#navbarCompainList > div > div.btn-group.open > ul > li:nth-child(1) > a"));
            button_Enable.click();
            Thread.sleep(1000);
            WebElement button_Start = agentPD.findElement(By.cssSelector("#navbarCompainList > div > button.btn.btn-success.btn-sm.navbar-btn"));
            button_Start.click();
        }
        agentPD.quit();
        log("Run powerdialer campaign with id = " + campaignId + ".", "INFO");
    }
}
