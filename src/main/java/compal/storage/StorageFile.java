package compal.storage;

import compal.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Represents file used to store COMPal.
 */
public class StorageFile implements Storage {
    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    private static final String saveFilePath = "./Compal.txt";
    private static final String binarySaveFilePath = "binary";
    private static final String userPreferencesFilePath = "./prefs.txt";

    /**
     * Prints message of storage initialized.
     */
    public StorageFile() {
        System.out.println("Storage:LOG: Storage Initialized!");
    }


    /**
     * Loads the arrayList as a binary stream.
     *
     * @return ArrayList of stored item found in file.
     */
    @Override
    public ArrayList<Task> loadCompal() {
        ArrayList<Task> tempList = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binarySaveFilePath));
            tempList = (ArrayList<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Storage:WARNING: Binary save-file not found");
        }
        return tempList;
    }

    /**
     * Loads and returns the username as a String.
     * TODO: logic error(Unknown user, return logic)
     *
     * @return Username.
     */
    @Override
    public String getUserName() {
        File f = new File(userPreferencesFilePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown User";
    }

    /**
     * Saves ArrayList of tasks into file.
     *
     * @param tasks ArrayList of task stored.
     */
    @Override
    public void saveCompal(ArrayList<Task> tasks) {
        try {
            ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(binarySaveFilePath));
            ois.writeObject(tasks);
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a string to a file.
     *
     * @param toSave   String to save into file.
     * @param filePath File path of file.
     */
    @Override
    public void saveString(String toSave, String filePath) {
        try {
            File f = new File(filePath);
            PrintWriter pw = new PrintWriter(f);
            pw.printf("%s\n", toSave);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Storage:WARNING: Save-file not found. Will generate new one.");
        }
    }

    /**
     * Stores username in file.
     * File is prefs.txt.
     *
     * @param name Username to store into file.
     */
    @Override
    public void storeUserName(String name) {
        saveString(name, userPreferencesFilePath);
    }

    /**
     * Takes in varargs strings containing details of a task (DateAndTime, Task ID, Task Type, Task Name and etc).
     * Returns a fully joined string (each component string is joined by underscores).
     *
     * @param properties Varargs strings that contain different properties of a task.
     * @return Joined string of all properties.
     */
    @Override
    public String generateStorageString(String... properties) {
        StringBuilder sb = new StringBuilder();
        for (String property : properties) {
            sb.append("_");
            sb.append(property);
        }
        return sb.toString();
    }
}
