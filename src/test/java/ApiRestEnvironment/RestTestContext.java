package ApiRestEnvironment;

import org.yaml.snakeyaml.Yaml;

import java.util.Map;

import static support.TestContext.getStream;
import static support.TestContext.teDataDirectory;

public class RestTestContext {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String JSON = "application/json";
    public static final String AUTH = "Authorization";

    // ---------------------------------------------------------------------------
    //  Rest Environment Data Handlers
    // ---------------------------------------------------------------------------
    // ---------------------------------------------------------------------------
    //   Tool getData: Get key value pairs from resources/data yml file
    //      Parameters: name of yml file = dataUPS.yml
    //      Output:     yml stream
    //       ====> use getGuiData()  when need to read strings or ints from yml
    // ---------------------------------------------------------------------------
    public static Map<String, Object> getData(String fileName) {
        String myDataDir = teDataDirectory;
        String myFilename = fileName;
        String myExtension = "yml";
        return new Yaml().load(getStream(myDataDir, myFilename, myExtension));
    }

    // ---------------------------------------------------------------------------
    //   Tool getStrData: Get key value pairs from resources/data yml file
    //      Parameters: name of yml file = dataUPS.yml
    //      Output:     yml stream
    //       ====> use getStrData()  when know you only read strings from yml
    // ---------------------------------------------------------------------------
    public static Map<String, String> getStrData(String fileName) {
        String myDataDir = teDataDirectory;
        String myFilename = fileName;
        String myExtension = "yml";
        return new Yaml().load(getStream(myDataDir, myFilename, myExtension));
    }
}
