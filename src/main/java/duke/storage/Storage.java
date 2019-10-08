package duke.storage;

import duke.exception.DukeException;
import duke.task.Booking;
import duke.task.Task;
import duke.tasklist.TaskList;


import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Handles the ability to read and write to the storage location.
 */
public class Storage {
    private static final ArrayList<Task> arrTaskList = new ArrayList<>();
    private final String filePath;

    /**
     * Constructor for the class Storage.
     *
     * @param filePath String containing the directory in which the tasks are to be stored
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**

     * Writing to file to save the task to file.
     *
     * @param taskList contains the task list
     */
    public void saveFile(TaskList taskList) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Task task : taskList.getTaskList()) {
                bufferedWriter.write(task.toSaveString() + "\n");
            }
            bufferedWriter.close();
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    /**
     * Load all the save tasks in the file.
     *
     * @return the list of tasks in taskList
     * @throws DukeException if Duke is not able to load the tasks from the file or unable to open the file
     */
    public ArrayList<Task> load() throws DukeException {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {

                if(content.split("\\|",6)[0].trim().equals("booking")) {

                    String customerName = content.split("\\|",6)[1].trim();
                    String customerContact = content.split("\\|",6)[2].trim();
                    String numberOfPax = content.split("\\|",6)[3].trim();
                    String bookingDate = content.split("\\|",6)[4].trim();
                    String orderName = content.split("\\|",6)[5].trim();
                    Task task = new Booking(customerName, customerContact, numberOfPax, bookingDate, orderName);
                    arrTaskList.add(task);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return arrTaskList;
    }

    private static void assignTaskMarker(String content, Task task) {
        if (content.charAt(4) == '+') {
            task.markAsDone();
        }
        arrTaskList.add(task);
    }
}
