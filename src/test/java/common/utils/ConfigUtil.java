package common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.Dimension;

public class ConfigUtil {

    private static Properties property;

    static {
        try (FileInputStream inputStream = new FileInputStream("src/test/resources/config_local.properties")){
            property = new Properties();
            property.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Config is not loaded");
        }
    }

    public static String getDefaultUrl() {
        return property.getProperty("default-url");
    }

    public static Dimension getDefaultSize() {
        return new Dimension(
                Integer.parseInt(property.getProperty("width")),
                Integer.parseInt(property.getProperty("height"))
                );
    }

    public static String getUsername() {
        return property.getProperty("username");
    }

    public static String getPassword() {
        return property.getProperty("password");
    }

    public static String getToken() {
        return property.getProperty("token");
    }

    public static String getAlbum() {
        return property.getProperty("album_id");
    }

    public static String getUser() {
        return property.getProperty("user_id");
    }

    public static String getImg() {
        return property.getProperty("image_name");
    }
}
