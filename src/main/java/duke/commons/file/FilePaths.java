package duke.commons.file;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * This class file defines all the resource config file path helper functions that
 * will be used in the storage component.
 */
public class FilePaths {

    private static Gson gson = new Gson();
    private static Type type = new TypeToken<HashMap<FilePathNames, String>>(){}.getType();

    protected static HashMap<FilePathNames, String> filePathsConfigMap = new HashMap<FilePathNames, String>();

    public FilePaths() {
        setFilePathsConfigMap();
    }

    /**
     * Reads the master file config and loads the location of all other config files into hashmap.
     * Also ensures all path names are stored in cross-system compatible file directories.
     */
    public static void setFilePathsConfigMap() {
        EnumSet.allOf(FilePathNames.class).forEach(FilePathName
                -> filePathsConfigMap.put(FilePathName, FilePathName.toString()));
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
