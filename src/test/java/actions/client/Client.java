package actions.client;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.net.InetAddress;

import static callsMethods.Methods.log;
import static callsMethods.Methods.sikuliClickElement;
import static callsMethods.Methods.sleep;

/**
 * Created by SChubuk on 11.04.2018.
 */
public class Client {

    public static void openCXphone(int waitTime) throws FindFailed, InterruptedException, IOException {

        Thread.sleep(1000);
        App cxphone = App.open("C:\\Program Files (x86)\\3CXPhone\\3CXPhone.exe");
        sikuliClickElement("closePhoneWindow");
        log("Open 3CXPhone window.", "DEBUG");

    }

    public static void cxAnswer() throws FindFailed, InterruptedException {
        cxAnswer(10, "Answer call on client side.");
    }

    public static void cxAnswer(String logMessage) throws FindFailed, InterruptedException {
        cxAnswer(10, logMessage);
    }

    public static void cxAnswer(int waitTime) throws FindFailed, InterruptedException {
        cxAnswer(waitTime, "Answer call on client side.");
    }

    public static void cxAnswer(int waitTime, String logMessage) throws FindFailed, InterruptedException {
        sikuliClickElement("button_3CXAcceptCall");
        sleep(1000);
        sikuliClickElement("closePhoneWindow");
        log(logMessage, "INFO");
    }

    public static void hangup(int line) throws FindFailed {
        App cxphone = App.open("C:\\Program Files (x86)\\3CXPhone\\3CXPhone.exe");
        org.sikuli.script.Pattern line_3CXLine2 = new org.sikuli.script.Pattern("C:\\SikuliImages\\line_3CXLine2.png");
        org.sikuli.script.Pattern closePhoneWindow = new org.sikuli.script.Pattern("C:\\SikuliImages\\closePhoneWindow.png");
        if (line == 2) {
            sikuliClickElement("line_3CXLine1");
        }

        sikuliClickElement("button_3CXHangupCall");
        log("Hangup the call on client side on the " + line + " line.", "INFO");
    }

    public static void focusCXphone(int waitTime) throws FindFailed, InterruptedException, IOException {
        String hostName = InetAddress.getLocalHost().getHostName();
        Thread.sleep(1000);
        if (hostName.equalsIgnoreCase("kv1-it-pc-jtest")) {
            App cxphone = App.open("C:\\Program Files (x86)\\3CXPhone\\3CXPhone.exe");
            Thread.sleep(waitTime);
        } else {
            App cxphone = App.open("C:\\Program Files (x86)\\3CXPhone\\3CXPhone.exe");
            Thread.sleep(waitTime);
        }
        log("3CXPhone opened and in focus.", "DEBUG");
    }
}
