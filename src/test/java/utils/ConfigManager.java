package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

/**
 * class - config handler
 *
 * @author Sergey Chevychelov {@literal <qa.schevychelov@gmail.com>}
 */
public class ConfigManager {
    private static Map<String, Config> configMap;
    private static TestEnv testEnv;

    static {
        Type itemsType = new TypeToken<Map<String, Config>>() {
        }.getType();
        String configPath = readConfig();
        configMap = new Gson().fromJson(configPath, itemsType);
        testEnv = getConfig().getEnv();
    }

    /**
     * read config file /src/test/resources/Config.json
     *
     * @return String
     */
    private static String readConfig() {
        StringBuilder builder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(System.getProperty("user.dir") + "/src/test/resources/Config.json"),
                StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * return Config instance for given system property passed with test.env
     *
     * @return Config
     */
    private static Config getConfig() {
        String envName = System.getProperty("test.env");
        if (configMap.get(envName) != null)
            return configMap.get(envName);
        else return configMap.get("test");
    }

    /**
     * @return String host
     */
    public static String getEnvUrl() {
        String hst = "";
        for (int i = 0; i < 10; i++) {
            try {
                hst = testEnv.getHost();
                break;
            } catch (Throwable ignored) {}
        }
        return hst;
    }

    public static String getToken() {
        String tkn = "";
        for (int i = 0; i < 10; i++) {
            try {
                tkn = testEnv.getToken();
                break;
            } catch (Throwable ignored) {}
        }
        return tkn;
    }
}