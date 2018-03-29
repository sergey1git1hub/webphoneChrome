package calls;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SChubuk on 12.01.2018.
 */
public class TestClass {
    @Test
    public static void test1(){
        Assert.assertTrue(true);

        Date date = new Date();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        // 2018-03-29 13:22:23.700

        String stringDate = df.format(date);

        System.out.println("Report Date: " + stringDate);

    }

   /* @Test
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
    }*/
}
