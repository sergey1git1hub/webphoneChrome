package autotests.calls.callOnTwoLines;

import actions.AgentAbstractionLayer;

import static actions.client.Client.cxAnswer;
import static actions.login.Login.openWebphoneLoginPage;

/**
 * Created by SChubuk on 05.10.2017.
 */

public class CallOnTwoLinesBaseClass {
   AgentAbstractionLayer agent = new AgentAbstractionLayer();

    public void callOnTwoLines() throws Exception {
        agent.createTestData();
        agent.Login();
        agent.callOnFirstLine();
        agent.callOnSecondLine();
        hangupHook();
        agent.setResultCodeAndCheckAvailableStatus();
    }

    public  void hangupHook() throws InterruptedException, Exception {

    }

}
