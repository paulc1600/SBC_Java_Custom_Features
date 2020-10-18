package API;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import static support.TestContext.teDataDirectory;

public class RestTestContext {
    public static String teApiBaseUrl = "";
    public static String teApiTitle = "";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String JSON = "application/json";
    public static final String AUTH = "Authorization";

    // ---------------------------------------------------------------
    //   initialize environment variables
    // ---------------------------------------------------------------
    public static void EnvironmentSetUp(String environmentName) {
        switch (environmentName) {
            case "careers":
                teApiBaseUrl = "https://skryabin.com/recruit/api/v1/";
                teApiTitle = "Careers Portal";
                break;
            case "google":
                teApiBaseUrl = "https://www.google.com/";
                teApiTitle = "Google";
                break;
            default:
                teApiBaseUrl = "https://skryabin.com/recruit/api/v1/";
                teApiTitle = "Default";
        }
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
        String path2file = System.getProperty("user.dir") + teDataDirectory + fileName + ".yml";
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
        String path2file = System.getProperty("user.dir") + teDataDirectory + fileName + ".yml";
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
