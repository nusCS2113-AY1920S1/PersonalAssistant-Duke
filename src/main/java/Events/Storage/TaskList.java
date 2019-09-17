package main.java.Events.Storage;

import Events.EventTypes.Deadline;
import Events.EventTypes.Event;
import Events.EventTypes.ToDo;
import main.java.Events.EventTypes.Task;
import main.java.Events.Formatting.DateObj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    public boolean addTask(Task task) {
        boolean succeeded;
        if (task instanceof ToDo) {
            this.taskArrayList.add(task);
            return true;
        }
        else {
            Task clashTask = clashTask(task);
            if (clashTask == null) {
                this.taskArrayList.add(task);
                return true;
            } else return false;
        }
    }

    /**
     * Adds repeated tasks to the list
     *
     * @param task Task to be repeated
     * @param period Period of the task to be repeated
     * @param repeatTime Times of repeating
     */
    public boolean addRecurringTask(Task task, int period, int repeatTime) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HHmm");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        DateObj taskDate = new DateObj(task.getDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskDate.javaDate);
        for (int addTaskCount = 1; addTaskCount <= repeatTime; addTaskCount++) {
            calendar.add(Calendar.DATE, addTaskCount*period);
            if (task instanceof Event) {
                String timeString = null;
                if (taskDate.format == 1) {
                    timeString = format1.format(calendar.getTime());
                }
                else if (taskDate.format == 2) {
                    timeString = format2.format(calendar.getTime());
                }
                Event repeatEvent = new Event(task.getDescription(), timeString);
                Task clashTask = clashTask(repeatEvent);
                if (clashTask == null) {
                    this.taskArrayList.add(repeatEvent);
                } else return false;
//
            }
            else if (task instanceof Deadline) {
                String timeString = null;
                if (taskDate.format == 1) {
                    timeString = format1.format(calendar.getTime());
                }
                else if (taskDate.format == 2) {
                    timeString = format2.format(calendar.getTime());
                }
                Deadline repeatDeadline = new Deadline(task.getDescription(), timeString);
                Task clashTask = clashTask(repeatDeadline);
                if (clashTask == null) {
                    this.taskArrayList.add(repeatDeadline);
                } else return false;
            }
        }
        return true;
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

    /**
     * Deletes a task from the list.
     *
     * @param taskNo Index of task to be deleted
     */
    public void deleteTask(int taskNo) {
        this.taskArrayList.remove(taskNo);
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
}
