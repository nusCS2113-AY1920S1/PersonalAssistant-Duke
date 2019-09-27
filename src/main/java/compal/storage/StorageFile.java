package compal.storage;

import compal.tasks.*;

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
        ArrayList<Task> tempList = new ArrayList<>();
        try {
            File f = new File("duke.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st;
            while ((st = br.readLine()) != null){
                Task t = null;
                String[] parts = st.split(" ");
                String taskType = parts[0];
                switch(taskType){
                    case "D":
                        t = new Deadline(parts[1],stringToPriority(parts[3]),parts[4]);
                        break;
                    case "DAT":
                        t = new DoAfterTasks(parts[1],stringToPriority(parts[3]),parts[4]);
                        break;
                    case "RT":
                        t = new RecurringTask(parts[1],stringToPriority(parts[3]),parts[4],parts[5]);
                        break;
                    case "E":
                        t = new Event(parts[1],stringToPriority(parts[3]),parts[4],parts[5]);
                        break;
                    case "FDT":
                        t = new FixedDurationTask(parts[1],stringToPriority(parts[3]),parts[4],parts[5],
                                Integer.parseInt(parts[6]),Integer.parseInt(parts[7]));
                        break;
                    default:
                        System.out.println("Storage:LOG: Could not parse text");

                }

                //set tasks completion and reminder status
                if (parts[2].equals("true"))
                    if (t != null) {
                    t.markAsDone();
                }
                if (parts[8].equals("true"))
                    if (t != null) {
                    t.setHasReminder();
                }

                //add created task to list
                if (t != null) {
                    tempList.add(t);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempList;
    }

    public Task.Priority stringToPriority(String priority){
        return Task.Priority.valueOf(priority.toLowerCase());
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
        return "";
    }

    /**
     * Saves ArrayList of tasks into file.
     *
     * @param tasks ArrayList of task stored.
     */
    @Override
    public void saveCompal(ArrayList<Task> tasks) {
        for (Task t:tasks){
            StringBuilder sb = new StringBuilder();
            sb.append(t.getAllDetailsAsString());
            saveString(sb.toString(),"duke.txt");
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


}
