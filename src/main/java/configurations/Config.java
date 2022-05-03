package configurations;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Config {

    public static final String ENV = System.getProperty("Env", "dev");

    public static final Properties PROPERTIES = new Properties();
    public static final String URI;
    public static final String HUB_URL;
    public static final String STACK_USER;
    public static final String STACK_KEY;
    public static final String REMOTE;


    static {
        loadProperties();

        URI = PROPERTIES.getProperty(ENV+".uri");
        STACK_USER = PROPERTIES.getProperty("stack.user");
        STACK_KEY = PROPERTIES.getProperty("stack.key");

        HUB_URL = "https://" + STACK_USER + ":" + STACK_KEY + URI;
        REMOTE = PROPERTIES.getProperty("remote");
    }

    private static void loadProperties() {
        try(InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("environment.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
