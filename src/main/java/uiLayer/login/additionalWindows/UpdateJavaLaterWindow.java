package uiLayer.login.additionalWindows;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import utils.SikuliAction;

/**
 * Created by SChubuk on 19.04.2018.
 */
public class UpdateJavaLaterWindow {
    SikuliAction sikuliAction = new SikuliAction();

    public void updateLater() {
        Thread thread2 = new Thread() {
            public void run() {
                try{
                sikuliAction.sikuliClickElement("checkbox_doNotAskAgain");
                sikuliAction.sikuliClickElement("option_updateJavaLater");
                } catch (Exception e){
                }
            }

        };
    }
}
