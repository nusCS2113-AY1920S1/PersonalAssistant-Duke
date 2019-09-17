package Events.Storage;

import Events.EventTypes.Deadline;
import Events.EventTypes.Event;
import Events.EventTypes.Task;
import Events.EventTypes.ToDo;
import Events.Formatting.DateObj;
import UserElements.UI;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Allows for access to the list of tasks currently stored, and editing that list of tasks.
 * Does NOT contain any methods for reading/writing to savefile.
 */
public class TaskList {

    /**
     * list of Model_Class.Task objects currently stored.
     */
    private ArrayList<Task> taskArrayList;

    /**
     * Creates new Model_Class.TaskList object.
     *
     * @param inputList list of strings containing all information extracted from save file
     */
    public TaskList(ArrayList<String> inputList) {
        taskArrayList = new ArrayList<Task>();
        for (String currLine : inputList) {
            boolean isDone = (currLine.charAt(4) == '\u2713');
            if (currLine.charAt(1) == 'T') {
                taskArrayList.add(new ToDo(currLine.substring(7), isDone));
            } else if (currLine.charAt(1) == 'E') {
                int posOfLine = currLine.indexOf("(at: ");
                taskArrayList.add(new Event(currLine.substring(7, posOfLine), currLine.substring(posOfLine + 5, currLine.length() - 1), isDone));
            } else if (currLine.charAt(1) == 'D') {
                int posOfLine = currLine.indexOf("(by: ");
                taskArrayList.add(new Deadline(currLine.substring(7, posOfLine), currLine.substring(posOfLine + 5, currLine.length() - 1), isDone));
            }
        }
    }

    /**
     * Adds a new task to the list
     *
     * @param task Model_Class.Task object to be added
     */
    public Task addTask(Task task) {
        boolean succeeded;
        if (task instanceof ToDo) {
            this.taskArrayList.add(task);
            return null;
        }
        else {
            Task clashTask = clashTask(task);
            if (clashTask == null) {
                this.taskArrayList.add(task);
                return null;
            } else return clashTask;
        }
    }

    private Task clashTask(Task task) {
        for (Task currTask : taskArrayList) {
            try {
                if (currTask.getDate().equals(task.getDate())) {
                    return currTask;
                }
            } catch (Exception e){
            }
        }
        return null;
    }

    private boolean checkForDateClash(String date) {
        DateObj dateObj = new DateObj(date);
        for (Task currTask : taskArrayList) {
            if (currTask.getDate().equals(dateObj.toOutputString())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Deletes a task from the list.
     *
     * @param taskNo Index of task to be deleted
     */
    public void deleteTask(int taskNo) {
        this.taskArrayList.remove(taskNo);
    }

    public void deleteTask(Task taskToDelete) {
        this.taskArrayList.remove(taskToDelete);
    }

    /**
     * Gets list of Model_Class.Task objects stored
     *
     * @return Array of TaskLists containing all tasks.
     */
    public ArrayList<Task> getTaskArrayList() {
        return this.taskArrayList;
    }

    /**
     * Gets number of tasks stored.
     *
     * @return number of tasks stored
     */
    public int getNumTasks() {
        return taskArrayList.size();
    }

    /**
     * Gets a specific task using indexing.
     *
     * @param index Index of task to be extracted
     * @return Model_Class.Task object of specified task
     */
    public Task getTask(int index) {
        return taskArrayList.get(index);
    }

    /**
     * Gets the entire list of tasks stored in String format
     *
     * @return String containing all tasks, separated by a newline.
     */
    public String listOfTasks_String() {
        String allTasks = "";
        for (int i = 0; i < taskArrayList.size(); ++i) {
            if (taskArrayList.get(i) == null) continue;
            int j = i + 1;
            allTasks += j + ". " + this.getTask(i).toString() + "\n";
        }
        return allTasks;
    }

    public void handleClash(Task addedTask, Task clashTask, UI ui) {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        int indexOfSpace = userInput.indexOf(" ");

        String command = userInput.substring(0, indexOfSpace);
        String continuation = userInput.substring(indexOfSpace + 1);

        if (command.equals("reschedule")) {
            String taskToReschedule = continuation.substring(0, continuation.indexOf(" "));
            String newDate = continuation.substring(continuation.indexOf(" ") + 1);
            boolean clashDetected = checkForDateClash(newDate);
            while(clashDetected) {
                ui.scheduleClash(clashTask);
                userInput = scanner.nextLine();
            }
            if (taskToReschedule.equals("old")) {
                clashTask.reschedule(newDate);
            } else if (taskToReschedule.equals("new")) {
                addedTask.reschedule(newDate);
            }
        }
    }
}
