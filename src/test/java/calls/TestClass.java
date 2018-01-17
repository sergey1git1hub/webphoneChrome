package calls;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by SChubuk on 12.01.2018.
 */
public class TestClass {
    @Test
    public static void test1(){
        Assert.assertTrue(true);
    }

    @Test
    public static void test2(){
        Assert.assertTrue(true);
    }

    @Test
    public static void test3(){
        Assert.assertTrue(true);
    }

    @Test
    public static void test4(){
        Assert.assertTrue(false);
    }

    @Test
    public static void test5(){
        Assert.assertTrue(false);
    }
}
