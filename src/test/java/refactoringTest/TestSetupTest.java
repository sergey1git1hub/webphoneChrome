package refactoringTest;

import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;
import utils.TestSetup;
import utils.logging.LoggingService;
import utils.logging.setupTeardown.TestSetupClass;

import java.io.IOException;

import static utils.BeforeAfter.loadProperties;

/**
 * Created by SChubuk on 13.04.2018.
 */
public class TestSetupTest {
    private static String testName = "testName";
    private TestSetupClass testSetup = new TestSetupClass();
    private LoggingService loggingService = LoggingService.getLoggingService();

    @Test
    public void testSetupTest() throws InterruptedException, FindFailed, IOException {
        loadProperties();
        testSetup.setup(testName);
        loggingService.log("Log goes here.", "INFO");
    }
}
