/**
 * File storage utilities
 *
 * @author tygq13
 * @author kuromono
 */
package cube.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


import cube.exception.DukeLoadingException;
import cube.storage.FoodStorage;
import cube.storage.RevenueStorage;
import cube.storage.StorageManager;
import cube.exception.DukeException;

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
     * @throws DukeLoadingException exception occurs when unable to create new file.
     */
    public void create(File file) throws DukeLoadingException {
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new DukeLoadingException(fileFullPath);
        }
    }

    /**
     * Returns true if the data file is available, otherwise makes a new data file and returns false.
     *
     * @return true if data file available, otherwise false.
     * @throws DukeLoadingException exception occurs when unable to create new file.
     */
    public boolean checkFileAvailable() throws DukeLoadingException {
        File file = new File(fileFullPath);
        if (file.exists()) {
            return true;
        } else {
            create(file);
            return false;
        }
    }

    /**
     * Loads serialized tasks objects from data file to an ArrayList.
     *
     * @return the list of tasks.
     * @throws DukeException exception occurs in reading from data file.
     */
    public StorageManager load() throws DukeException {
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
                //e.printStackTrace();
                throw new DukeLoadingException(fileFullPath);
            }
        }
        return storageManager;
    }

    /**
     * Saves the whole list of tasks into data file.
     *
     * @param storageManager containing FoodStorage & RevenueStorage data to save.
     * @throws DukeException exception happens in writing to the data file.
     */
    public void save(StorageManager storageManager) throws DukeException {
        checkFileAvailable();
        try {
            FileOutputStream fileSave = new FileOutputStream(fileFullPath, false);
            ObjectOutputStream out = new ObjectOutputStream(fileSave);
            out.writeObject(storageManager);
            out.close();
            fileSave.close();
        } catch (IOException e) {
            throw new DukeLoadingException(fileFullPath);
        }
    }
}