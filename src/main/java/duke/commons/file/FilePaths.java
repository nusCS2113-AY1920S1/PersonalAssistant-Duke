package duke.commons.file;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import duke.storage.FileUtil;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * This class file defines all the resource config file path helper functions that
 * will be used in the storage component.
 */
public class FilePaths {

    private static String filePathsMasterConfigStr = "duke/commons/file/filepaths.json";
    private static Gson gson = new Gson();
    private static Type type = new TypeToken<HashMap<FilePathNames, String>>(){}.getType();

    public enum FilePathNames {
        FILE_PATH_USER_MEALS_FILE,
        FILE_PATH_DEFAULT_MEAL_FILE,
        FILE_PATH_GOAL_FILE,
        FILE_PATH_USER_FILE,
        FILE_PATH_AUTOCORRECT_FILE,
        FILE_PATH_TRANSACTION_FILE,
        FILE_PATH_MASTER_HELP_FILE
    }

    protected static HashMap<FilePathNames, String> filePathsConfigMap = new HashMap<FilePathNames, String>();

    public FilePaths() {
        filePathsMasterConfigStr = FileUtil.getSystemFilePathStr(filePathsMasterConfigStr);
        setFilePathsConfigMap(FileUtil.readResourceFile(filePathsMasterConfigStr));
    }

    /**
     * Reads the master file config and loads the location of all other config files into hashmap.
     * Also ensures all path names are stored in cross-system compatible file directories.
     * @param bufferedReader Reader instance of master file config.
     */
    public static void setFilePathsConfigMap(BufferedReader bufferedReader) {
        filePathsConfigMap = gson.fromJson(bufferedReader, type);
        for (FilePathNames pathName : filePathsConfigMap.keySet()) {
            String defaultPathStr = filePathsConfigMap.get(pathName);
            String crossCompatiblePathStr = FileUtil.getSystemFilePathStr(defaultPathStr);
            filePathsConfigMap.replace(pathName, crossCompatiblePathStr);
        }
    }

    public String getFilePathStr(FilePathNames filePathName) {
        return filePathsConfigMap.get(filePathName);
    }
}
