package duke.storage;

import java.io.*;
import java.util.ArrayList;

import duke.exceptions.DukeException;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.ToDo;

/**
 * Storage is a public class, a storage class encapsulates the filePath to read from and write to
 * @author Ivan Andika Lie
 */
public class Storage {
    private String filePath;
    private String line = null;

    /**
     * This is a constructor of Storage class
     * @param filePath this is the file path to read from and write to
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     *     to be added in that TaskList
     * @return the ArrayList of task loaded from the file
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */
    public ArrayList<Task> load() throws DukeException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                //TODO: Parse the line
                loadFile(line, tasks);
            }
            bufferedReader.close();

        } catch(FileNotFoundException e) {
            throw new DukeException("Unable to open file '" + filePath + "'");
        } catch (IOException e) {
            throw new DukeException("Error reading file '" + filePath + "'");
        }
        return tasks;
    }

    /**
     * This function acts as a line by line parser from the text file which is used to load a particular type of task
     * @param line the line input from the input file
     * @param tasks the task arraylist that will store the tasks from the input file
     */
    private static void loadFile(String line, ArrayList<Task> tasks) {
        String[] splitLine = line.split(" \\| ");
        String taskType = splitLine[0];
        boolean isDone = splitLine[1].equals("1");
        String description = splitLine[2];

        String timeFrame = "";
        if (taskType.equals("D") || taskType.equals("E")) {
            timeFrame = splitLine[3];
        }
        if (taskType.equals("T")) {
            loadToDo(tasks, description, isDone);
        } else if (taskType.equals("D")) {
            loadDeadline(tasks, description, timeFrame, isDone);
        } else if (taskType.equals("E")) {
            loadEvent(tasks, description, timeFrame, isDone);
        }

    }

    /**
     * This function will load a todo line and push it to the task arraylist
     * @param tasks the task arraylist that will store the tasks from the input file
     * @param description the task specified
     * @param isDone whether the todo task is done
     */
    //TODO: make such that the loadFile only need to call one function only
    private static void loadToDo(ArrayList<Task> tasks, String description, boolean isDone) {
        ToDo newToDo = new ToDo(description);
        if (isDone) {
            newToDo.markAsDone();
        }
        tasks.add(newToDo);
    }

    /** This function will load a deadline line and push it to the task arraylist
     * @param tasks the task arraylist that will store the tasks from the input file
     * @param description the task specified
     * @param by the deadline of the deadline task
     * @param isDone whether the deadline task is done
     */
    private static void loadDeadline(ArrayList<Task> tasks, String description, String by, boolean isDone) {
        Deadline newDeadline = new Deadline(description, by);
        if (isDone) {
            newDeadline.markAsDone();
        }
        tasks.add(newDeadline);
    }

    /**
     * This function will load a event line and push it to the task arraylist
     * @param tasks the task arraylist that will store the tasks from the input file
     * @param description the event specified
     * @param duration the duration of the event
     * @param isDone
     */
    private static void loadEvent(ArrayList<Task> tasks, String description, String duration, boolean isDone) {
        Event newEvent = new Event(description, duration);
        if (isDone) {
            newEvent.markAsDone();
        }
        tasks.add(newEvent);
    }

    /**
     * This is a function that will update the input/output file from the current arraylisto of tasks
     * @param tasks the task arraylist that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void updateFile(ArrayList<Task> tasks) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < tasks.size(); i++) {
                Task currentTask = tasks.get(i);
                String currentLine = currentTask.toString();
                if (i > 0) {
                    bufferedWriter.newLine();
                }
                String status = "0";
                if (currentTask.getisDone()) {
                    status = "1";
                }
                bufferedWriter.write(currentTask.getType() + " | " + status + " | " + currentTask.getDescription());
                if ((currentTask.getType()).equals("E")) {
                    String timeFrame = (currentLine.split("at: ", 2))[1];
                    bufferedWriter.write(" | " + timeFrame.substring(0, timeFrame.length() - 1));
                }
                else if ((currentTask.getType()).equals("D")) {
                    String timeFrame = (currentLine.split("by: ", 2))[1];
                    bufferedWriter.write(" | " + timeFrame.substring(0, timeFrame.length() - 1));
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file '" + filePath + "'");
            e.printStackTrace();
        }
    }
}
