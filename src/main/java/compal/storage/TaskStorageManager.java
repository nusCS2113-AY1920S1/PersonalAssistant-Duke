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
public class TaskStorageManager implements Storage<ArrayList<Task>> {
    TaskStorageParser tsp;
    String taskStorageFilePath;

    /**
     * Prints message of storage initialized.
     */
    public TaskStorageManager(String filePath) {
        tsp = new TaskStorageParser();
        taskStorageFilePath = filePath;
        System.out.println("TaskStorageManager:LOG: Storage Initialized!");
    }


    /**
     * Creates and loads task objects based on save text file into arraylist, then returns the arraylist.
     *
     * @return ArrayList of stored item found in file.
     * @author jaedonkey
     */
    @Override
    public ArrayList<Task> loadData() {
        ArrayList<Task> tempList = new ArrayList<>();
        try {
            File f = new File(taskStorageFilePath);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st;
            while ((st = br.readLine()) != null) {
                Task t;
                System.out.println("TaskStorageManager:LOG: Task read:" + st);
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
                if (parts[8].equals("true")) {
                    t.setHasReminder();
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
            File f = new File(taskStorageFilePath);
            PrintWriter pw = new PrintWriter(f);
            pw.printf("%s\n", sb);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Storage:WARNING: Save-file not found. Will generate new one.");
        }


    }





}
