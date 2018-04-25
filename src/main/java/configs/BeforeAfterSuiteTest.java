package configs;

import org.testng.annotations.AfterTest;

/**
 * Created by SChubuk on 19.04.2018.
 */
public class BeforeAfterSuiteTest {

    @AfterTest
    private void teardown() {
        System.out.println("Can't access driver here.");
    }

}
