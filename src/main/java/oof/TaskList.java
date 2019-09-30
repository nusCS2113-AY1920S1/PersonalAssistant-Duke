package oof;

import oof.task.Task;

import java.util.ArrayList;

/**
 * Represents a list of Task objects.
 */
public class TaskList {

    protected ArrayList<Task> arr;

    /**
     * Constructor for TaskList.
     *
     * @param arr TaskList that contains Task objects.
     */
    public TaskList(ArrayList<Task> arr) {
        this.arr = arr;
    }

    /**
     * Constructor for TaskList.
     */
    public TaskList() {
        this.arr = new ArrayList<>();
    }

    /**
     * Retrieves all the Task objects in TaskList.
     *
     * @return TaskList containing all its Task objects.
     */
    public ArrayList<Task> getTasks() {
        return arr;
    }

    /**
     * Retrieves the number of Task objects in the TaskList.
     *
     * @return Number of Task objects in the TaskList.
     */
    public int getSize() {
        return arr.size();
    }

    /**
     * Retrieves the Task object at a particular index.
     *
     * @param index Index of Task object, specified by the user.
     * @return Task object at a particular index.
     */
    public Task getTask(int index) {
        return arr.get(index);
    }

    /**
     * Adds a Task object to the TaskList.
     *
     * @param task Task object to be added to TaskList.
     */
    public void addTask(Task task) {
        arr.add(task);
    }

    /**
     * Adds a Task object to a specific index in the TaskList.
     *
     * @param index Index to be inserted.
     * @param task  Task to be added.
     */
    public void addTaskToIndex(int index, Task task) {
        arr.add(index, task);
    }

    /**
     * Deletes a Task object from the TaskList.
     *
     * @param index Index of Task object, specified by the user
     */
    public void deleteTask(int index) {
        arr.remove(index);
    }
}
