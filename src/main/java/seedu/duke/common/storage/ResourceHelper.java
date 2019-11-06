package seedu.duke.common.storage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import seedu.duke.ui.UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResourceHelper {

    public static void prepareTestFile() {
        Path path = StorageHelper.prepareDataPath("test_template.txt");
        try {
            StorageHelper.saveToFile(path, prepareTestJsonObject().toString());
        } catch (IOException | JSONException e) {
            UI.getInstance().showError("Prepare test template file failed with exception...");
        }
    }

    private static JSONObject prepareTestJsonObject() throws IOException, JSONException {
        List<String> testDataFilenames = getTestDataFilenames();
        List<String> testEmailFilenames = getTestEmailFilenames();
        JSONObject testJson = new JSONObject();
        JSONArray testDataArray = new JSONArray();
        JSONArray testEmailArray = new JSONArray();
        for (String testDataFilename : testDataFilenames) {
            testDataArray.put(prepareTestDataJsonObject(testDataFilename));
        }
        for (String testEmailFilename : testEmailFilenames) {
            testEmailArray.put(prepareTestEmailJsonObject(testEmailFilename));
        }
        testJson.put("testData", testDataArray);
        testJson.put("testEmail", testEmailArray);
        return testJson;
    }

    private static Path prepareTestDataPath(String filename) {
        return Path.of(".", "data", "test_data", filename);
    }

    private static Path prepareTestEmailPath(String filename) {
        return Path.of(".", "data", "test_data", "emails", filename);
    }

    private static JSONObject prepareTestDataJsonObject(String filename) throws IOException, JSONException {
        Path path = prepareTestDataPath(filename);
        return prepareJsonObjectFromPath(filename, path);
    }

    private static JSONObject prepareTestEmailJsonObject(String filename) throws IOException, JSONException {
        Path path = prepareTestEmailPath(filename);
        return prepareJsonObjectFromPath(filename, path);
    }

    private static JSONObject prepareJsonObjectFromPath(String filename, Path path) throws IOException, JSONException {
        String content = StorageHelper.readFromFile(path);
        JSONObject json = new JSONObject();
        json.put(filename, content);
        return json;
    }

    /**
     * Get all filenames from the test data file.
     *
     * @return a list of filenames
     * @author RoflcoptrException from https://stackoverflow.com/questions/5694385/getting-the-filenames-of
     * -all-files-in-a-folder
     */
    private static List<String> getTestDataFilenames() {
        File folder = new File(Path.of(".", "data", "test_data").toString());
        return getFilenamesFromFolder(folder);
    }

    private static List<String> getTestEmailFilenames() {
        File folder = new File(Path.of(".", "data", "test_data", "emails").toString());
        return getFilenamesFromFolder(folder);
    }

    private static List<String> getFilenamesFromFolder(File folder) {
        File[] listOfFiles = folder.listFiles();

        List<String> filenames = new ArrayList<>();
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                filenames.add(listOfFile.getName());
            }
        }
        return filenames;
    }



}
