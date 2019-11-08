package diyeats.commons.file;

import java.util.EnumSet;
import java.util.HashMap;

//@@author HashirZahir
/**
 * This class file defines all the resource config file path helper functions that
 * will be used in the storage component.
 */
public class FilePaths {

    private static HashMap<FilePathNames, String> filePathsConfigMap = new HashMap<FilePathNames, String>();

    public FilePaths() {
        setFilePathsConfigMap();
    }

    /**
     * Reads the master file config and loads the location of all other config files into hashmap.
     * Also ensures all path names are stored in cross-system compatible file directories.
     */
    public static void setFilePathsConfigMap() {
        EnumSet.allOf(FilePathNames.class).forEach(filePathName ->
                filePathsConfigMap.put(filePathName, filePathName.toString()));
        for (FilePathNames pathName : filePathsConfigMap.keySet()) {
            String defaultPathStr = filePathsConfigMap.get(pathName);
            String crossCompatiblePathStr = FileUtil.getSystemFilePathStr(defaultPathStr);
            filePathsConfigMap.replace(pathName, crossCompatiblePathStr);
        }
    }

    public String getFilePathStr(FilePathNames filePathName) {
        return filePathsConfigMap.get(filePathName);
    }

    public void setTestPathConfigMap() {
        for (FilePathNames pathName : filePathsConfigMap.keySet()) {
            String defaultPathStr = filePathsConfigMap.get(pathName);
            String crossCompatiblePathStr = FileUtil.getSystemFilePathStr(defaultPathStr);
            filePathsConfigMap.replace(pathName, crossCompatiblePathStr.replace("main", "test"));
        }
    }
}
