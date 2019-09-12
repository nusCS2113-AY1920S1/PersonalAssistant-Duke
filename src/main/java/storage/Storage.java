package storage;

import exception.DukeException;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;
import tasklist.TaskList;

import java.io.*;
import java.util.ArrayList;

/**
 * Handles the ability to read and write to the storage location
 */
public class Storage {
    private static final ArrayList<Task> arrTaskList = new ArrayList<>();
    private final String filePath;

    /**
     * Constructor for the class Storage
     * @param filePath String containing the directory in which the tasks are to be stored
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Writing to file to save the task to file
     * @param taskList contains the task list
     */
    public void saveFile(TaskList taskList){
        try{
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(Task task: taskList.getTaskList()) {
                bufferedWriter.write(task.toSaveString() + "\n");
            }
            bufferedWriter.close();
        } catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    /**
     * Load all the save tasks in the file
     * @return the list of tasks in taskList
     * @throws DukeException if Duke is not able to load the tasks from the file or unable to open the file
     */
    public ArrayList<Task> load() throws DukeException {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while((content = bufferedReader.readLine())!= null){
                if(content.charAt(0) == 'T') {
                    String details = content.substring(8);
                    Task task = new Todo(details);
                    if (content.charAt(4) == '+') {
                        task.markAsDone();
                    }
                    arrTaskList.add(task);
                }else {
                    //need to escape character in string for "|" by adding "\\" in front of "|"
                    //if not the split will be on the wrong place
                    String[] split = content.substring(8).split(" \\| ", 2);
                    if (content.charAt(0) == 'D') {
                        Task task = new Deadline(split[0], split[1]);
                        if (content.charAt(4) == '+') {
                            task.markAsDone();
                        }
                        arrTaskList.add(task);
                    } else if (content.charAt(0) == 'E') {
                        Task task = new Event(split[0], split[1]);
                        if (content.charAt(4) == '+') {
                            task.markAsDone();
                        }
                        arrTaskList.add(task);
                    }
                }
            }
            fileReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }
        return arrTaskList;
    }
}
