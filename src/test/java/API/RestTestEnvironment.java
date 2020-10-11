package API;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class RestTestEnvironment {
    public static String teBaseUrl = "";
    public static String teApiTitle = "";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String JSON = "application/json";
    public static final String AUTH = "Authorization";


    public static void EnvironmentSetUp(String environmentName) {
        switch (environmentName) {
            case "careers":
                teBaseUrl = "https://skryabin.com/recruit/api/v1/";
                teApiTitle = "Careers Portal";
                break;
            case "google":
                teBaseUrl = "https://www.google.com/";
                teApiTitle = "Google";
                break;
            default:
                teBaseUrl = "https://skryabin.com/recruit/api/v1/";
                teApiTitle = "Default";
        }

        System.out.println("\n   Navigate: Open URL");
        System.out.println("================================================");
        System.out.println(" Base URL:  " + teBaseUrl);
        System.out.println(" API Title: " + teApiTitle);
        System.out.println("================================================");
    }

    // ---------------------------------------------------------------
    //  Shared TestData Object Support
    // ---------------------------------------------------------------
    private static Map<String, Object> testData = new HashMap<>();

    public static Map<String, Object> getTestDataMap(String key) {
        return (Map<String, Object>) testData.get(key);
    }

    public static int getTestDataInteger(String key) {
        System.out.println("********* Get:: key = " + key + "   Contents = " + testData.get(key) + "   *********");
        int intTestValue = parseInt(String.valueOf(testData.get(key)));
        return intTestValue;
    }

    public static String getTestDataString(String key) {
        return (String) testData.get(key);
    }

    public static void setTestData(String key, Object value) {
        System.out.println("********* Put:: key = " + key + "   Contents = " + String.valueOf(value)  + "   *********");
        testData.put(key, value);
    }

    // ---------------------------------------------------------------------------
    //  Rest Environment Data Handlers
    // ---------------------------------------------------------------------------
    // ---------------------------------------------------------------------------
    //   Tool getGuiData: Get key value pairs from resources/data yml file
    //      Parameters: name of yml file = dataUPS.yml
    //      Output:     yml stream
    //       ====> use getData()  when need to read strings or ints from yml
    //       ====> use getStrData()  when know you only read strings from yml
    //          (called from Gherkin)
    // ---------------------------------------------------------------------------
    public static Map<String, Object> getData(String fileName) {
        String path2file = System.getProperty("user.dir") + "/src/test/resources/data/RestTestData/" + fileName + ".yml";
        File fileTD = new File(path2file);
        FileInputStream stream = null;

        try {
            stream = new FileInputStream(fileTD);
        }
        catch (FileNotFoundException exception) {
            System.err.println(exception.getMessage());
        }
        return new Yaml().load(stream);
    }

    // ---------------------------------------------------------------------------
    //   Tool getGuiStrData: Get key value pairs from resources/data yml file
    //      Parameters: name of yml file = dataUPS.yml
    //      Output:     yml stream
    //       ====> use getData()  when need to read strings or ints from yml
    //       ====> use getStrData()  when know you only read strings from yml
    //          (called from Gherkin)
    // ---------------------------------------------------------------------------
    public static Map<String, String> getStrData(String fileName) {
        String path2file = System.getProperty("user.dir") + "/src/test/resources/data/RestTestData/" + fileName + ".yml";
        File fileTD = new File(path2file);
        FileInputStream stream = null;

        try {
            stream = new FileInputStream(fileTD);
        }
        catch (FileNotFoundException exception) {
            System.err.println(exception.getMessage());
        }
        return new Yaml().load(stream);
    }
}
