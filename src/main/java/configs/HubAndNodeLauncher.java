package configs;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by SChubuk on 23.04.2018.
 */
public class HubAndNodeLauncher {

    //FIND APPROPRIATE SOLUTION WHERE Runtime.getRuntime().exec() RETURNS!!!
    //https://stackoverflow.com/questions/5483830/process-waitfor-never-returns

    //don't forget to update cd {{launchHubScriptLocation}} in batch script
    private String launchHubScriptLocation = new File("src\\main\\resources\\batchScripts\\launchHubAndNode" +
            "\\launchHub\\launch-selenium-hub.bat").getCanonicalPath();
    private String launchNodeScriptLocation = new File("src/main/resources/batchScripts/launchHubAndNode/" +
            "launchNode/launch-selenium-node.bat").getCanonicalPath();


    public HubAndNodeLauncher() throws Exception {
    }


    public  void launchHubAndNode() throws Exception {
  /*      System.out.println(launchHubScriptLocation);
        System.out.println(launchNodeScriptLocation);*/

       // new String[]{"c:\\Program Files\\do.exe" used to deal with whitespaces
        Thread launchHubProcessThread = new Thread(new Runnable(){
            public void run(){

                try {
                    Process launchHubProcess = Runtime.getRuntime().exec(new String[]{launchHubScriptLocation});
                    printProcessStream(launchHubProcess);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        Thread launchNodeProcessThread = new Thread(new Runnable(){
            public void run(){
                try {
                    Process launchNodeProcess = Runtime.getRuntime().exec(new String[]{launchNodeScriptLocation});
                    printProcessStream(launchNodeProcess);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        launchHubProcessThread.start();
        Thread.sleep(200);
        launchNodeProcessThread.start();


       /* Process launchHubProcess = Runtime.getRuntime().exec(new String[]{launchHubScriptLocation});
        printProcessStream(launchHubProcess);
        Process launchNodeProcess = Runtime.getRuntime().exec(new String[]{launchNodeScriptLocation});
        printProcessStream(launchNodeProcess);
        System.out.println("Hub and node has been launched.");*/
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

        Thread.sleep(5000);
        Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
    }
}

