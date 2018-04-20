package utils;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 * Created by SChubuk on 20.04.2018.
 */
public class SikuliAction {

    public static void sikuliClickElement(String elementName) throws Exception {
        sikuliClickElement(elementName, 10);
    }

    public static void sikuliClickElement(String elementName, int timeout) throws Exception {

        Screen screen = new Screen();
        org.sikuli.script.Pattern element = new org.sikuli.script.Pattern("C:\\SikuliImages\\" + elementName + ".png");
        screen.wait(element, timeout);
        screen.click(element);

    }

}
