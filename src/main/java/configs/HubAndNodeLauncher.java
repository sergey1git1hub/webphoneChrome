package configs;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by SChubuk on 23.04.2018.
 */
public class HubAndNodeLauncher {

    private String launchHubScriptLocation = "src/main/resources/batchScripts/launchHubAndNode/launchHub/launch-selenium-hab.bat";
    private String launchNodeScriptLocation = "src/main/resources/batchScripts/launchHubAndNode/launchNode/launch-selenium-node.bat";


    public  void launchHubAndNode() throws IOException {
        Process launchHubProcess = Runtime.getRuntime().exec(launchHubScriptLocation);
        printProcessStream(launchHubProcess);
        Process launchNodeProcess = Runtime.getRuntime().exec(launchNodeScriptLocation);
        printProcessStream(launchNodeProcess);
        System.out.println("Hub and node has been launched.");
    }

    public  void printProcessStream(Process p) throws IOException {
        String line;
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
    }

    //Test should be written to detect any errors in process

    @Test
    private void hubAndNodeLauncherTest() throws Exception{
        HubAndNodeLauncher hubAndNodeLauncher = new HubAndNodeLauncher();
        hubAndNodeLauncher.launchHubAndNode();
    }
}

