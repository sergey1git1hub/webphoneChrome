package utils;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by SChubuk on 23.04.2018.
 */
public class PropertiesLoader {

    private String propertiesFileLocation = "src\\main\\resources\\autotest.properties";

    public  void loadProperties() {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(propertiesFileLocation);
            prop.load(input);
            for (String name : prop.stringPropertyNames()) {
                String value = prop.getProperty(name);
                if (System.getProperty(name) == null) {
                    System.setProperty(name, value);
                    //nicePrint(name + "=" + System.getProperty(name));
                }

            }



        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void nicePrint(String text) {
        System.out.println("=============================================");
        System.out.println(text);
        System.out.println("=============================================");
    }

    @BeforeSuite
    @Test
    private void propertiesLoaderTest(){
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadProperties();
        String baseDriverPaht = System.getProperty("baseDriverPath");
        Assert.assertNotNull(baseDriverPaht);
        System.out.println(baseDriverPaht);

    }
}
