package data;

import java.net.UnknownHostException;

/**
 * Created by SChubuk on 04.10.2017.
 */
public class OperaData extends Data {
    public OperaData() throws UnknownHostException {
        super();
        browser = "opera";
        webphoneUrl = "http://172.21.7.239/gbwebphone/";
        method = "usual";
    }

}
