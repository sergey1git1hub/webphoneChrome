package refactoringTest;

import org.junit.Test;
import utils.logging.LoggingService;

/**
 * Created by SChubuk on 13.04.2018.
 */


public class LoggingServiceTest {

    @Test
    public void loggingServiceTest() throws Exception{
        LoggingService loggingService = LoggingService.getLoggingService();
        loggingService.createLogFile("testFile");
        loggingService.log("testString", "INFO");
    }

}
