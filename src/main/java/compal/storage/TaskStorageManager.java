package compal.storage;

import compal.commons.LogUtils;
import compal.model.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

//@@author jaedonkey

/**
 * Represents file used to store COMPal.
 */
public class TaskStorageManager implements Storage<ArrayList<Task>> {

    public static final String MESSAGE_ERROR_MK_FILE = "Error when trying to creating file.";
    public static final String DEFAULT_STORAGE = "./tasks.txt";
    private static final Logger logger = LogUtils.getLogger(TaskStorageManager.class);

    private TaskStorageParser tsp;

    /**
     * Prints message of storage initialized.
     */
    public TaskStorageManager() {
        createFile();
        tsp = new TaskStorageParser();
    }


    /**
     * Creates the directory for storing data if it does not exist.
     */
    public void createFile() {
        File file = new File(DEFAULT_STORAGE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException se) {
                logger.warning(MESSAGE_ERROR_MK_FILE);
            }
        }
    }

    /**
     * Creates and loads task objects based on save text file into arraylist, then returns the arraylist.
     *
     * @return ArrayList of stored item found in file.
     * @author jaedonkey
     */
    @Override
    public ArrayList<Task> loadData() {
        logger.info("Loading tasks.txt into arraylist");
        ArrayList<Task> tempList = new ArrayList<>();
        try {
            File f = new File(DEFAULT_STORAGE);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st;
            while ((st = br.readLine()) != null) {
                Task t;
                String[] parts = st.split("_");

                //check if it is a valid task read
                if (parts.length <= 1) {
                    continue;
                }

                t = tsp.parseData(parts);

                //set tasks completion and reminder status
                if (parts[3].equals("true")) {
                    t.markAsDone();
                }
                if (parts[9].equals("true")) {
                    t.setHasReminder(true);
                }

                //set task id
                t.setId(Integer.parseInt(parts[0]));

                //add created task to list
                tempList.add(t);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempList;
    }


    /**
     * Saves ArrayList of tasks into file.
     *
     * @param tasks ArrayList of task stored.
     * @author jaedonkey
     */
    @Override
    public void saveData(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();

        for (Task t : tasks) {
            sb.append(t.getAllDetailsAsString());
            sb.append("\n");
        }

        try {
            File f = new File(DEFAULT_STORAGE);
            PrintWriter pw = new PrintWriter(f);
            pw.printf("%s\n", sb);
            pw.close();
        } catch (FileNotFoundException e) {
            logger.info("Storage:WARNING: Save-file not found. Will generate new one.");
        }
        logger.info("File save successfully.");
    }


}
