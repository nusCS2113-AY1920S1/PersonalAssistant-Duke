package common;

import task.Task;

import java.util.ArrayList;

/**
 * TaskList class to handle methods related to the tasklist of the user.
 */
public class TaskList {
    private ArrayList<Task> tasklist;

    /**
     * Copies an existing ArrayList containing the Task object into a newly instantiated TaskList.
     * @param tasklist Tasklist to be copied.
     */
    public TaskList(ArrayList<Task> tasklist) {
        this.tasklist = tasklist;
    }

    /**
     * Creates a new TaskList with an empty ArrayList.
     */
    public TaskList() {
        this.tasklist = new ArrayList<>();
    }

    /**
     * Adds a new Task to the TaskList.
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        tasklist.add(task);
    }

    /**
     * Deletes a Task from the TaskList.
     * @param numdelete Index of the Task in the TaskList to delete.
     */
    public void deleteTask(int numdelete) {
        tasklist.remove(numdelete);
    }

    /**
     * Gets the Task based on the index given.
     * @param number Index of the Task in the TaskList.
     * @return The Task of the index given.
     */
    public Task get(int number) {
        return tasklist.get(number);
    }

    /**
     * Gives the size of the TaskList.
     * @return TaskList size.
     */
    public int size() {
        return tasklist.size();
    }

    /**
     * Converts the TaskList into an ArrayList containing the Task object.
     * @return ArrayList containing the Task object.
     */
    public ArrayList<Task> returnArrayList() {
        return tasklist;
    }
}
