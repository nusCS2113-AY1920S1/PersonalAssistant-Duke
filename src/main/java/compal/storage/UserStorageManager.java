package compal.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserStorageManager implements Storage<String> {

    public static final String DEFAULT_USER_STORAGE = "./prefs.txt";

    public UserStorageManager() {
        createFile();
    }

    /**
     * Creates the directory for storing data if it does not exist.
     */
    public void createFile() {
        File file = new File(DEFAULT_USER_STORAGE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException se) {
                System.out.println(DEFAULT_USER_STORAGE);
            }
        }
    }

    /**
     * Returns the user's name.
     *
     * @return String user's name
     */
    @Override
    public String loadData() {
        File f = new File(DEFAULT_USER_STORAGE);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Saves the user's name.
     *
     * @param toSave user's name
     */
    public void saveData(String toSave) {
        try {
            File f = new File(DEFAULT_USER_STORAGE);
            PrintWriter pw = new PrintWriter(f);
            pw.printf("%s\n", toSave);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Storage:WARNING: Save-file not found. Will generate new one.");
        }
    }


}
