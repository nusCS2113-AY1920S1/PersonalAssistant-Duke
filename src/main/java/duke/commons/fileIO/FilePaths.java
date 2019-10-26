package duke.commons.fileIO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * This class file defines all the resource config file path helper functions that
 * will be used in the storage component.
 */
public class FilePaths {

    private static String filePathsMasterConfigStr = "duke/Data/filepaths.json";
    private static Gson gson = new Gson();
    private static Type type = new TypeToken<HashMap<FILE_PATH_NAMES, String>>(){}.getType();

    public enum FILE_PATH_NAMES {
        FILE_PATH_USER_MEALS_FILE,
        FILE_PATH_DEFAULT_MEAL_FILE,
        FILE_PATH_GOAL_FILE,
        FILE_PATH_USER_FILE,
        FILE_PATH_AUTOCORRECT_FILE
    }

    protected static HashMap<FILE_PATH_NAMES, String> filePathsConfigMap = new HashMap<FILE_PATH_NAMES, String>();

    public FilePaths() {
        filePathsMasterConfigStr = FileUtil.getSystemFilePathStr(filePathsMasterConfigStr);
        setFilePathsConfigMap(FileUtil.readResourceFile(filePathsMasterConfigStr));
    }

    public static void setFilePathsConfigMap(BufferedReader bufferedReader) {
        filePathsConfigMap = gson.fromJson(bufferedReader, type);
    }

    public String getFilePathStr(FILE_PATH_NAMES filePathName) {
        return filePathsConfigMap.get(filePathName);
    }
}
