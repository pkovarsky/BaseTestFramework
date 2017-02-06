package utils;

import org.testng.Reporter;

import java.io.*;
import java.util.Properties;

/**
 * Created by antonreznikov on 2/4/17.
 */
public class ContextResolver {

    private static final String ENV_PROPERTIES = "env";
    private static ContextResolver instance = new ContextResolver();
    private Properties envMap = new Properties();
    private Properties gradleMap = new Properties();
    private Properties generalMap = new Properties();

    private ContextResolver() {
        init();
    }

    public static ContextResolver getInstance() {
        if (instance == null) {
            instance = new ContextResolver();
        }
        return instance;
    }

    public void init() {
        //loadPropertiesFromClasspath(envMap, ENV_PROPERTIES);
        loadEnvProperties(gradleMap);

        //generalMap.putAll(envMap);
        generalMap.putAll(gradleMap);
        if (System.getProperty("env.url") != null) {
            generalMap.setProperty("env.url", System.getProperty("env.url"));
        }
        if (System.getProperty("browser") != null) {
            generalMap.setProperty("browser", System.getProperty("browser"));
        }
    }

    public String getProperty(String key) {
        String result = (String) generalMap.get(key);
        if (result != null) {
            return result;
        } else {
            throw new NullPointerException("Property " + key + " was not found");
        }
    }

    public void clear() {
        generalMap.clear();
    }

    private String getFullFileName(String fileName) {
        return fileName + ".properties";
    }

    private void loadEnvProperties(Properties props) {
        try {
            String fileName = "env.properties";
            String path = System.getProperty("user.dir");
            Reader resource = new FileReader(new File(path + "/src/main/resources/properties/" + fileName));
            if (resource != null) {
                props.load(resource);
            }
        } catch (IOException e) {
            Reporter.log("Missing or corrupt property file", true);
        }
    }

    private void loadPropertiesFromClasspath(Properties props, String fileName) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream resourceAsStream = classLoader.getResourceAsStream(getFullFileName(fileName));


            if (resourceAsStream != null) {
                props.load(resourceAsStream);
            }
        } catch (IOException e) {
            Reporter.log("Missing or corrupt property file", true);
        }
    }
}
