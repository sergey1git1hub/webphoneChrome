package data;

import org.openqa.selenium.WebDriver;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static callsMethods.Methods.log;
import static utils.Flags.isLocal;

/**
 * Created by SChubuk on 04.10.2017.
 */
public class Data {
    WebDriver driver;

    public String browser;
    public String webphoneUrl;
    public String method;
    public String username;
    public String group;
    public String number1;
    public String number2;
    public String dbTable;
    public String dbPhoneNumber;
    public static String localhostName = "KV1-EM-PC-14";
    public static int retryLimit = 0;
    public static String password = "1";
    public static String PDUrl = "http://172.21.24.109:8087/gbpowerdialer/#/login";

    public Data() throws UnknownHostException {

        if (isLocal()) {
            username = "81046";
            number1 = "94949";
            number2 = "94948";
        } else {
            username = "81048";
            number1 = "94944";
            number2 = "94947";
            retryLimit = 2;
        }


    }

    public static Data createData() throws UnknownHostException {
        Data data;
        String host_Name = InetAddress.getLocalHost().getHostName();
        String browser = System.getProperty("browserName");
        if (browser.equalsIgnoreCase("chrome")) {
            data = new ChromeData();
        } else {
            data = new IEData();
        }
        try {
            log(data.toString() +  " created with createData() method ", "DEBUG");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
