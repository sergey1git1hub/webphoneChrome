package callsMethods;

import org.openqa.selenium.*;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import utils.TestSetup;

import java.io.*;

import static utils.Flags.isIE;

import static utils.NativeServiceUpdate.updateNativeService;


/**
 * Created by SChubuk on 04.10.2017.
 */

public class Methods {
    public static String browser;
    public static boolean onJenkins;
    static boolean killProcess = true;
    static boolean debug = Boolean.parseBoolean(System.getProperty("debug"));
    public static File manualLogFile;

    static boolean fast = true;



    //@Deprecated
    public static void executeJavaScriptOrClick(WebDriver driver, WebElement element, String script) {
        executeJavaScriptOrClick(driver, element, script, false);
    }

    public static void executeJavaScriptOrClick(WebDriver driver, WebElement element, String script, Boolean inAllBrowsers) {
        if (isIE(driver)||inAllBrowsers) {
            try {
                if (driver instanceof JavascriptExecutor) {
                    ((JavascriptExecutor) driver)
                            .executeScript(script, element);
                    //System.out.println("Button " + element.toString() + " pressed by javascript.");
                }
            } catch (Exception e) {
                if (debug == true)
                    e.printStackTrace();
                else log("JavaScript execution error!", "INFO");
            }
        } else {
            element.click();
        }
    }
    //@Deprecated
    public static void executeJavaScriptOrClick(WebDriver driver, WebElement element, Boolean inAllBrowsers) {
        if ((isIE(driver)||inAllBrowsers)) {
            try {
                if (driver instanceof JavascriptExecutor) {
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();", element);
                    //System.out.println("Button " + element.toString() + " pressed by javascript.");
                }
            } catch (Exception e) {
                if (debug == true)
                    e.printStackTrace();
                else log("JavaScript execution error!", "INFO");
            }
        } else {
            element.click();
        }


    }

    public static void executeJavaScriptOrClick(WebDriver driver, WebElement element){
        executeJavaScriptOrClick(driver, element, true);
    }



    @Deprecated(/**Use executeJavaScriptOrClick(driver, element) instead"*/)
    public static void clickIEelement(WebDriver driver, WebElement element) {
        executeJavaScriptOrClick(driver, element);
        log("IE element clicked through JavascriptExecutor.", "DEBUG");
    }



    public static void callToQueue() throws FindFailed, InterruptedException, IOException {
        App cxphone = App.open("C:\\Program Files (x86)\\3CXPhone\\3CXPhone.exe");

        sikuliClickElement("numberFour");
        sikuliClickElement("numberNine");
        sikuliClickElement("numberZero");
        sikuliClickElement("button3CXCall");
        if (fast = false)
            Thread.sleep(1000);

        sikuliClickElement("closePhoneWindow");
        log("Call to queue (490).", "INFO");

    }

    public static void nicePrint(String text) {
        System.out.println("=============================================");
        System.out.println(text);
        System.out.println("=============================================");
    }

    public static void log(String text, String logLevel) { //INFO, DEBUG, ERROR

        String LOGLEVEL = System.getProperty("LOGLEVEL");
        if (logLevel.equalsIgnoreCase(LOGLEVEL)||logLevel.equalsIgnoreCase("CONSOLE")) {
            System.out.println(text);
            writeLog(text);
        }

    }


    //open and close file here
    public static void writeLog(String text) {
        manualLogFile = TestSetup.manualLogFile;
        if (Boolean.getBoolean("withPound")) {
            text = "# " + text;
        }
        try {
            FileWriter writer = new FileWriter(manualLogFile, true);
            writer.write(text + "\n");
            writer.close();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public static void sikuliClickElement(String elementName) throws FindFailed {
        sikuliClickElement(elementName, 10);
    }

    public static void sikuliClickElement(String elementName, int timeout) throws FindFailed {
        Screen screen = new Screen();
        org.sikuli.script.Pattern element = new org.sikuli.script.Pattern("C:\\SikuliImages\\" + elementName + ".png");
        screen.wait(element, timeout);
        screen.click(element);
        log("Click " + elementName + ".", "CONSOLE");
    }

    public static void sleep(int timeMilliseconds) throws InterruptedException {
        Boolean slow = Boolean.getBoolean("run.slow");
        if(slow){
            Thread.sleep(timeMilliseconds);
        }
    }


}
