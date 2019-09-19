package duke.task;

import java.util.ArrayList;

/**
 * Represents the list of tasks.
 */
public class TaskList {

    private static ArrayList<duke.task.Task> taskList;

    /**
     * Creates a new TaskList if no TaskList is given.
      */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Initializes the task list to a given task list.
     * @param tasks ArrayList object containing previously initialized tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.taskList = tasks;
    }

    /**
     * Adds a Task to the task list.
     * @param value Task to be added to the list
     */
    public void addToArrayList(Task value) {
        this.taskList.add(value);
    }

    /**
     * Deletes a Task from the task list.
     * @param num The index of the task in the task list to be removed
     * @return The Task that is to be deleted
     */
    public Task deleteFromArrayList(int num) {
        return this.taskList.remove(num);
    }

    /**
     * Returns the current size of the task list.
     * @return The number of tasks in the task list
     */
    public static int getSize() {
        return taskList.size();
    }

    /**
     * Returns the task corresponding to the index given.
     * @param num Index of the Task to be found
     * @return The Task corresponding to the index
     */
    public static Task getTask(int num) {
        return taskList.get(num);
    }

    /**
     * Returns the index of the given task.
     * @param task Task to search for within array
     * @return Index of the given task, else -1
     */
    public static int getTaskIndex(Task task) {
        for (int i = 0; i < taskList.size(); ++i) {
            if (taskList.get(i) == task) {
                return i;
            }
        }
        return -1;
    }
}
