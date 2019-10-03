/**
 * Handling of file operation utilities in Cube.
 *
 * @author tygq13
 */
package cube.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import cube.exception.CubeException;
import cube.exception.CubeLoadingException;
import cube.storage.FoodStorage;
import cube.storage.RevenueStorage;
import cube.storage.StorageManager;

public class FileUtil {
    private String filePath;
    private String fileFullPath;
    private static final String fileName = "cube.dat";

    /**
     * Constructor with one argument.
     *
     * @param filePath the directory path where data will be stored.
     */
    public FileUtil(String filePath) {
        this.filePath = filePath;
        this.fileFullPath = filePath + File.separator + fileName;
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
    public boolean checkFileAvailable() throws CubeLoadingException {
        File file = new File(fileFullPath);
        if (file.exists()) {
            return true;
        } else {
            create(file);
            return false;
        }
    }

    /**
     * Loads serialized StorageManager object from the file.
     *
     * @return the list of tasks.
     * @throws CubeLoadingException exception occurs in reading from data file.
     */
    public StorageManager load() throws CubeException {
        FoodStorage foodStorage = new FoodStorage();
        RevenueStorage revenueStorage = new RevenueStorage();
        StorageManager storageManager = new StorageManager(foodStorage, revenueStorage);
        if (checkFileAvailable()) {
            System.out.println("Loading file from : " + fileFullPath);
            // read from file
            try {
                FileInputStream fileInput = new FileInputStream(fileFullPath);
                ObjectInputStream in = new ObjectInputStream(fileInput);

                storageManager = (StorageManager)in.readObject();

                fileInput.close();
                in.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new CubeLoadingException(fileFullPath);
            }
        }
        return storageManager;
    }

    /**
     * Saves the StorageManage object into a file.
     *
     * @param storageManager containing FoodStorage & RevenueStorage data to save.
     * @throws CubeException exception happens in writing to the data file.
     */
    public void save(StorageManager storageManager) throws CubeException {
        checkFileAvailable();
        try {
            FileOutputStream fileSave = new FileOutputStream(fileFullPath, false);
            ObjectOutputStream out = new ObjectOutputStream(fileSave);
            out.writeObject(storageManager);
            out.close();
            fileSave.close();
        } catch (IOException e) {
            throw new CubeLoadingException(fileFullPath);
        }
    }
}