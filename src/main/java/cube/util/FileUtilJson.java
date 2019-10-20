/**
 * Handling of JSON File Read/Write operations in Cube
 *
 * @author kuromono
 */
package cube.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import cube.exception.CubeException;
import cube.exception.CubeLoadingException;
import cube.storage.ConfigStorage;
import cube.storage.StorageManager;

public class FileUtilJson {
    private String filePath;
    private String fileFullPath;
    private String configFileFullPath;
    private static final String fileName = "cube.json";
    private static final String configFileName = "config.json";

    /**
     * Constructor with one argument.
     *
     * @param filePath the directory path where data will be stored.
     */
    public FileUtilJson(String filePath) {
        this.filePath = filePath;
        this.fileFullPath = filePath + File.separator + fileName;
        this.configFileFullPath = filePath + File.separator + configFileName;
    }

    /**
     * Creates the parent directory and file.
     *
     * @param file the file at which should be created.
     * @throws CubeLoadingException exception occurs when unable to create new file.
     */
    public void create(File file) throws CubeLoadingException {
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new CubeLoadingException(fileFullPath);
        }
    }

    /**
     * Returns true if the data file is available, otherwise makes a new data file and returns false.
     *
     * @return true if data file available, otherwise false.
     * @throws CubeLoadingException exception occurs when unable to create new file.
     */
    public boolean checkFileAvailable(String fullPath) throws CubeLoadingException {
        File file = new File(fullPath);
        if (file.exists()) {
            return true;
        } else {
            create(file);
            return false;
        }
    }

    /**
     * Loads serialized StorageManager object from the JSON file.
     *
     * @return the list of tasks.
     * @throws CubeLoadingException exception occurs in reading from data file.
     */
    public StorageManager load() throws CubeException {
        StorageManager storageManager = new StorageManager();
        if (checkFileAvailable(fileFullPath)) {
            System.out.println("Loading file from : " + fileFullPath);

            try {
                File fileSave = new File(fileFullPath);
                ObjectMapper mapper = new ObjectMapper();
                storageManager = mapper.readValue(fileSave, StorageManager.class);
            } catch (IOException e) {
                e.printStackTrace();
                throw new CubeLoadingException(fileFullPath);
            }
        }
        return storageManager;
    }

    /**
     * Saves the StorageManage object into a JSON file.
     *
     * @param storageManager containing FoodStorage & RevenueStorage data to save.
     * @throws CubeException exception happens in writing to the data file.
     */
    public void save(StorageManager storageManager) throws CubeException {
        checkFileAvailable(fileFullPath);
        try {
            File fileSave = new File(fileFullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(fileSave, storageManager);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CubeLoadingException(fileFullPath);
        }
    }

    public ConfigStorage loadConfig() throws CubeException {
        ConfigStorage configStorage = new ConfigStorage();
        if (checkFileAvailable(configFileFullPath)) {
            System.out.println("Loading file from : " + configFileFullPath);

            try {
                File fileSave = new File(configFileFullPath);
                ObjectMapper mapper = new ObjectMapper();
                configStorage = mapper.readValue(fileSave, ConfigStorage.class);
            } catch (IOException e) {
                e.printStackTrace();
                throw new CubeLoadingException(configFileFullPath);
            }
        }
        return configStorage;
    }

    public void saveConfig(ConfigStorage configStorage) throws CubeException {
        checkFileAvailable(configFileFullPath);
        System.out.println("haha");
        try {
            File fileSave = new File(configFileFullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(fileSave, configStorage);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CubeLoadingException(configFileFullPath);
        }
    }
}
