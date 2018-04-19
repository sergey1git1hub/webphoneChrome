package utils;

/**
 * Created by SChubuk on 12.01.2018.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.testng.*;
import org.testng.xml.XmlSuite;

public class SuiteListener implements ISuiteListener {


    public void onStart(ISuite suite) {

    }

    public void onFinish(ISuite suite) {
        String suiteName = suite.getName();
        Map<String, ISuiteResult> suiteResults = suite.getResults();
        for (ISuiteResult sr : suiteResults.values()) {
            ITestContext tc = sr.getTestContext();
            int failed =  tc.getFailedTests().getAllResults().size();
            Set<ITestResult> allResults = sr.getTestContext().getFailedTests().getAllResults();
            if(failed<=2){
                for (ITestResult result : allResults) {
                    result.setStatus(1);
                }
            }

        }
    }
}