package duke.storage;

import duke.exception.DukeException;
import duke.task.Task;
import duke.list.tasklist.TaskList;

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
/*    public ArrayList<Task> load() throws DukeException {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                if (content.charAt(0) == 'T') {
                    if (content.charAt(1) == 'S') {
                        String[] split = content.substring(9).split(" \\| ", 2);
                        Task task = new TentativeScheduling(split[0], split[1]);
                        if (content.charAt(5) == '+') {
                            task.markAsDone();
                        }
                        arrTaskList.add(task);
                    } else {
                        String details = content.substring(8);
                        Task task = new Todo(details);
                        if (content.charAt(4) == '+') {
                            task.markAsDone();
                        }
                        arrTaskList.add(task);
                    }
                } else {
                    //need to escape character in string for "|" by adding "\\" in front of "|"
                    //if not the split will be on the wrong place
                    String[] split = content.substring(8).split(" \\| ");
                    if (content.charAt(0) == 'D') {
                        Task task = new Deadline(split[0], split[1]);
                        assignTaskMarker(content, task);
                    } else if (content.charAt(0) == 'E') {
                        Task task = new Event(split[0], split[1]);
                        assignTaskMarker(content, task);
                    } else if (content.charAt(0) == 'F') {
                        Task task = new Duration(split[0], split[1]);
                        assignTaskMarker(content, task);
                    } else if (content.charAt(0) == 'P') {
                        if (split.length == 3) {
                            Task task = new Period(split[0], split[1], split[2]);
                            assignTaskMarker(content, task);
                        }
                    } else if (content.charAt(0) == 'R') {
                        if (split.length == 2) { // daily
                            Task task = new Recurring(split[0], split[1], "");
                            assignTaskMarker(content, task);
                        } else if (split.length == 3) { // weekly, monthly, yearly
                            Task task = new Recurring(split[0], split[1], split[2]);
                            assignTaskMarker(content, task);
                        }
                    }
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch (IOException | ParseException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }
        return arrTaskList;
    }*/

    private static void assignTaskMarker(String content, Task task) {
        if (content.charAt(4) == '+') {
            task.markAsDone();
        }
        arrTaskList.add(task);
    }
}
