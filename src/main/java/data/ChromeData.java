package data;

import java.net.UnknownHostException;

/**
 * Created by SChubuk on 04.10.2017.
 */
public class ChromeData extends Data {
    public ChromeData() throws UnknownHostException {
        super();
        browser = "chrome";
        webphoneUrl = "http://172.21.7.239/gbwebphone/";
        method = "usual";
    }

}
