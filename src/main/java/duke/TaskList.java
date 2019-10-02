package duke;

import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import duke.exceptions.BadInputException;
import duke.items.DateTime;
import duke.items.Deadline;
import duke.items.Event;
import duke.items.Task;
import duke.items.Todo;
import duke.enums.TaskType;

/**
 * Manages the list of (different types of classes),
 * including all the methods to modify the list:
 * Adding each of the 3 types, print, delete, mark as done, search.
 */

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> savedFile) { taskList = savedFile; }

    public TaskList() { taskList = new ArrayList<Task>(); }

    public ArrayList<Task> getTaskList() { return taskList; }

    public int getSize() { return taskList.size(); }

    /**
     * Adds a task to the tasklist.
     *
     * @return True if item was added successfully.
     */
    public boolean addItem(TaskType type, String description) {
        taskList.add(new Todo(description));
        return true;
    }

    /**
     * Adds a ToDo item to the list.
     *
     * @param type        TaskType enum MUST BE TODO.
     * @param description User input description of task
     * @param hrs         Duration of task.
     * @return true if item was added successfully.
     */
    public boolean addItem(TaskType type, String description, int hrs) {
        if (type != TaskType.TODO) {
            return false;
        }
        taskList.add(new Todo(description, hrs));

        return true;
    }

    /**
     * Adds a TODO, DEADLINE or EVENT item to the list.
     *
     * @param type        TaskType enum of task to be added.
     * @param description User input description of task.
     * @param dateTimes   Vararg of DateTimes that are to be added.
     * @return true if item was added successfully.
     */
    public boolean addItem(TaskType type, String description, DateTime... dateTimes) throws BadInputException {
            switch (type) {
            case TODO:
                taskList.add(new Todo(description));
                break;

            case DEADLINE:
                taskList.add(new Deadline(description, dateTimes[0]));
                break;

            case EVENT: // Throws the BadInputException
                taskList.add(new Event(description, dateTimes[0], dateTimes[1]));
                break;

            default:
                return false;
            }

        return true;
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }

    /**
     * Deletes a task of the user's choice.
     *
     * @param i the index of the task to be deleted.
     */
    public void deleteTask(int i) { taskList.remove(i); }

    // TODO: Deprecated, remove in future commit - Raghav
    /**
     * Prints error message if a nonexistent task index is accessed.
     * Prints the task list for user to choose again.
     */
    private void printTaskNonexistent() {
        System.out.println("That task doesn't exist! Please check the available tasks again: ");
        // TODO: I don't even know how to begin explaining the changes to make here..
        //printList();
    }

    /**
     * Looks for undone deadlines within the next 5 Days and prints the task.
     */
    public void printReminders() {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        long millisInFiveDays = 5 * 24 * 60 * 60 * 1000;

        for (Task task : taskList) {
            if (task instanceof Deadline && !task.getIsDone()) {
                Deadline deadline = (Deadline) task;
                long timeDifference = deadline.getDate().getTime().getTime() - now.getTime();
                if (timeDifference <= millisInFiveDays && timeDifference > 0) {
                    task.printTaskDetails();
                }
            }
        }
    }
}
