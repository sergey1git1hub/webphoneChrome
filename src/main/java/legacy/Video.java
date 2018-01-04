package legacy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by SChubuk on 04.01.2018.
 */
public class Video {

    static String propertiesFilePath = "src\\test\\resources\\video.properties";
    static String dynamicVideoPath;
    static String logFilePath;
    static String localBrowser = "chrome";

    static Properties videoPropeties;

    public static void createVideoPropertiesFile() throws IOException {
        //create properties file if not exists and add static properties to it
        File propertiesFile = new File(propertiesFilePath);
        if (!propertiesFile.exists()) {
            propertiesFile.getParentFile().mkdirs();
            propertiesFile.createNewFile();
        }
    }

    public static void loadPropertiesFromFile() throws IOException {
        videoPropeties = new Properties();
        //load Properties from file
        FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
        videoPropeties.load(fileInputStream);
        fileInputStream.close();
    }

    public static void createVideoFolder(){

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        Date date = new Date();
        dynamicVideoPath = "videoAndLogs\\" + localBrowser + "VideoAndLogs" + dateFormat.format(date).replaceAll("\\s", "");
        new File(dynamicVideoPath).mkdirs();
    }

    public static void addPropertyToVideoFolder() throws IOException {
        //add/modify property video.folder
        videoPropeties.setProperty("video.folder", "${user home}" + "\\" + dynamicVideoPath);
        System.setProperty("video.folder", dynamicVideoPath);

        //save properies to back to file
        FileOutputStream fileOutputStream = new FileOutputStream(propertiesFilePath);
        videoPropeties.store(fileOutputStream, "");
    }


    public static void configureVideoBeforeTest(){
        try {
            loadPropertiesFromFile();
            createVideoFolder();
            addPropertyToVideoFolder();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
