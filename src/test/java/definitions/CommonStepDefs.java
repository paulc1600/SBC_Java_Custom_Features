package definitions;

import java.util.Map;

import static ApiRestEnvironment.RestTestContext.getStrData;
import static support.TestContext.getTimestamp;

public class CommonStepDefs {
    // --------------------------------------------------------------
    //  Read and Prepare a Position Record -- Careers API Envr
    // --------------------------------------------------------------
    public static Map<String, String> getPosition(String filename) {
        Map<String, String> position = getStrData(filename);
        String title = position.get("title");
        position.put("title", title + getTimestamp());
        return position;
    }

    // --------------------------------------------------------------
    //  Read and Prepare a Candidate Record -- Careers API Envr
    // --------------------------------------------------------------
    public static Map<String, String> getCandidate(String filename) {
        Map<String, String> candidate = getStrData(filename);
        String email = candidate.get("email");
        String[] emailComp = email.split("@");
        candidate.put("email", emailComp[0] + getTimestamp() + "@" + emailComp[1]);
        return candidate;
    }

    // --------------------------------------------------------------
    //  Read and Prepare a Application Record -- Careers API Envr
    // --------------------------------------------------------------
    public static Map<String, String> getApplication(String filename) {
        Map<String, String> application = getStrData(filename);
        // String email = application.get("email");
        // String[] emailComp = email.split("@");
        // application.put("email", emailComp[0] + getTimestamp() + "@" + emailComp[1]);
        return application;
    }

}
