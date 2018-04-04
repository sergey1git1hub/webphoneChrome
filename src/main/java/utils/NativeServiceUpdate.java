package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static callsMethods.Methods.log;

/**
 * Created by SChubuk on 04.04.2018.
 */
public class NativeServiceUpdate {

    public static void updateNativeService() throws IOException {
        if (Boolean.getBoolean("updateService")) {
            updateNativeService(true);
        }

    }

    public static void updateNativeService(Boolean argument) throws IOException {

        Process uninstallProcess = Runtime.getRuntime().exec("src/resources/batch/uninstallns.bat");
        printProcessStream(uninstallProcess);
        Process installProcess = Runtime.getRuntime().exec("src/resources/batch/installns.bat");
        printProcessStream(installProcess);
        System.out.println("Native service updated before running autotests.");

    }

    public static void printProcessStream(Process p) throws IOException {
        String line;
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
    }

    public static void waitForServiceUpdate(final WebDriver driver) {
       /* Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    WebDriverWait waitForDownloadButton = new WebDriverWait(driver, 30);
                    waitForDownloadButton.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#btn_ef_positive")));
                    updateNativeService(true);
                } catch (Exception e) {
                    log("There is no service update window.", "DEBUG");
                }
            }
        });
        t1.start();*/
    }

    public static void main(String[] args) throws IOException {
        updateNativeService();
    }
}
