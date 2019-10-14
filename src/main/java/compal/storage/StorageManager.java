package compal.storage;

import compal.model.tasks.AcadTask;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.RecurringTask;
import compal.model.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Represents file used to store COMPal.
 */
public class StorageManager implements Storage {
    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    private static final String saveFilePath = "./Compal.txt";
    private static final String userPreferencesFilePath = "./prefs.txt";
    private static final String SYMBOL_LECT = "LECT";
    private static final String SYMBOL_TUT = "TUT";
    private static final String SYMBOL_SECT = "SECT";
    private static final String SYMBOL_LAB = "LAB";
    private static final String SYMBOL_ACAD = "ACAD";
    private static final String SYMBOL_RECUR = "RT";
    private static final String SYMBOL_DEADLINE = "D";
    private static final String SYMBOL_EVENT = "E";

    /**
     * Prints message of storage initialized.
     */
    public StorageManager() {
        System.out.println("Storage:LOG: Storage Initialized!");
    }


    /**
     * Creates and loads task objects based on save text file into arraylist, then returns arraylist.
     *
     * @return ArrayList of stored item found in file.
     * @author jaedonkey
     */
    @Override
    public ArrayList<Task> loadCompal() {
        ArrayList<Task> tempList = new ArrayList<>();
        try {
            File f = new File("duke.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st;
            while ((st = br.readLine()) != null) {
                Task t;
                System.out.println("StorageManager:LOG: Task read:" + st);
                String[] parts = st.split("_");
                String taskType = parts[0];
                switch (taskType) {
                case SYMBOL_DEADLINE:
                    t = new Deadline(parts[1], stringToPriority(parts[3]), parts[4], parts[6]);
                    break;
                case SYMBOL_LECT:
                case SYMBOL_TUT:
                case SYMBOL_SECT:
                case SYMBOL_LAB:
                case SYMBOL_ACAD:
                    t = new AcadTask(parts[1], stringToPriority(parts[3]), parts[4], parts[5], parts[6], taskType);
                    break;
                case SYMBOL_RECUR:
                    t = new RecurringTask(parts[1], stringToPriority(parts[3]), parts[4], parts[5], parts[6]);
                    break;
                case SYMBOL_EVENT:
                    t = new Event(parts[1], stringToPriority(parts[3]), parts[4], parts[5], parts[6]);
                    break;
                default:
                    System.out.println("Storage:LOG: Could not parse text. Returning what we managed to parse.");
                    return tempList;
                }

                //set tasks completion and reminder status
                if (parts[2].equals("true")) {
                    t.markAsDone();
                }
                if (parts[7].equals("true")) {
                    t.setHasReminder();
                }

                //add created task to list
                tempList.add(t);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempList;
    }

    /**
     * Returns Priority from a String describing the priority level.
     *
     * @param priority task priority string
     * @return Priority enum
     */
    public Task.Priority stringToPriority(String priority) {
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
     * @author jaedonkey
     */
    @Override
    public void saveCompal(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        for (Task t : tasks) {
            sb.append(t.getAllDetailsAsString());
            sb.append("\n");
        }
        saveString(sb.toString(), "duke.txt");
    }

    /**
     * Saves a string to a file.
     *
     * @param toSave   String to save into file.
     * @param filePath File path of file.
     * @author jaedonkey
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
     * @author jaedonkey
     */
    @Override
    public void storeUserName(String name) {
        saveString(name, userPreferencesFilePath);
    }


}
