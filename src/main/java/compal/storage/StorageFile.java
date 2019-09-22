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
    private static final String saveFilePath = "./duke.txt";
    private static final String binarySaveFilePath = "binary";
    private static final String userPreferencesFilePath = "./prefs.txt";


    public StorageFile() {
        System.out.println("Storage:LOG: Storage Initialized!");
    }

    /**
     * Preloads the arraylist as a binary stream if there is anything to load at all.
     *
     * @return tempList The arraylist of stored item found in file.
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
     * Loads and returns the user's name as a String.
     * TODO: logic error(Unknown user, return logic)
     *
     * @return String username
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
     * Used to save the arraylist of task into the file.
     *
     * @param tasks ArrayList of task stored
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
     * Saves the string toSave to the saveFilePath just as it is.
     *
     * @param toSave String
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
     * Stores username in prefs.txt .
     */
    @Override
    public void storeUserName(String name) {
        saveString(name, userPreferencesFilePath);
    }

    /**
     * Takes in varargs strings containing details of a task (DateAndTime, Task ID, Task Type, Task Name and etc).
     * Returns a fully joined string (each component string is joined by underscores) .
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
