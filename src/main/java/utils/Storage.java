package utils;

import tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * deals with loading tasks from the file and saving tasks in the file
 */
public class Storage {

    /**
     * file that store the data
     */
    private File dataFile;

    /**
     * input stream to get the file input
     */
    private InputStream is;

    /**
     * constructor of Storage, pass the data to dataFile by file path
     *
     * @param filePath path of the file that store the task list
     */
    public Storage(String filePath) {
        dataFile = new File(filePath);
    }

    /**
     * Read the data stored in hard disk to taskList.
     *
     * @return an ArrayList of Task, which is the task list
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task.tasks = tasks;
        try {
            is = new FileInputStream(dataFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                tasks.add(Parser.dataLine(line));
            }
            br.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Save the tasks data to the hard disk.
     *
     * @param taskList the array list of tasks to be saved.
     * @return if this method executed successfully
     */
    public boolean store(ArrayList<Task> taskList) {
        String output = "";
        for (int i = 0; i < taskList.size(); i++) {
            output += taskList.get(i).dataString() + "\n";
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile));
            bw.write(output);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
