package compal.storage;

import compal.model.tasks.Task;

import java.io.*;
import java.util.ArrayList;

public class UserStorageManager implements Storage<String> {
    String userStorageFilePath;

    public UserStorageManager(String userStorageFilePath) {
        this.userStorageFilePath = userStorageFilePath;
    }

    /**
     * Returns the user's name.
     * @return String user's name
     */
    @Override
    public String loadData() {
        File f = new File(userStorageFilePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Saves the user's name
     * @param toSave user's name
     */
    public void saveData(String toSave) {
        try {
            File f = new File(userStorageFilePath);
            PrintWriter pw = new PrintWriter(f);
            pw.printf("%s\n", toSave);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Storage:WARNING: Save-file not found. Will generate new one.");
        }
    }




}
