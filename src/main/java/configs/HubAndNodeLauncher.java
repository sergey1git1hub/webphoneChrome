package configs;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by SChubuk on 23.04.2018.
 */
public class HubAndNodeLauncher {

    //don't forget to update cd {{launchHubScriptLocation}} in batch script
    private String launchHubScriptLocation = new File("src\\main\\resources\\batchScripts\\launchHubAndNode" +
            "\\launchHub\\launch-selenium-hub.bat").getCanonicalPath();
    private String launchNodeScriptLocation = new File("src/main/resources/batchScripts/launchHubAndNode/" +
            "launchNode/launch-selenium-node.bat").getCanonicalPath();


    public HubAndNodeLauncher() throws Exception {
    }


    public void launchHubAndNode() throws Exception {

        Process launchHubProcess = Runtime.getRuntime().exec(new String[]{launchHubScriptLocation});
        printProcessStream(launchHubProcess);
        Process launchNodeProcess = Runtime.getRuntime().exec(new String[]{launchNodeScriptLocation});
        printProcessStream(launchNodeProcess);
        System.out.println("Hub and node has been launched.");
    }


    public  void printProcessStream(Process p) throws Exception {
        String line;
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
    }

    //Test should be written to detect any errors in process

    @Test
    private void hubAndNodeLauncherTest() throws Exception {
        HubAndNodeLauncher hubAndNodeLauncher = new HubAndNodeLauncher();
        hubAndNodeLauncher.launchHubAndNode();

    }


}
