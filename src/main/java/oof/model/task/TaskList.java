package oof.model.task;

import java.util.ArrayList;

/**
 * Represents a list of Task objects.
 */
public class TaskList {

    private ArrayList<Task> tasks;

    /**
     * Constructor for TaskList.
     *
     * @param tasks TaskList that contains Task objects.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructor for TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Retrieves all the Task objects in TaskList.
     *
     * @return TaskList containing all its Task objects.
     */
    public ArrayList<Task> getTaskList() {
        return tasks;
    }

    /**
     * Retrieves the number of Task objects in the TaskList.
     *
     * @return Number of Task objects in the TaskList.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Retrieves the Task object at a particular index.
     *
     * @param index Index of Task object, specified by the user.
     * @return Task object at a particular index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a Task object to the TaskList.
     *
     * @param task Task object to be added to TaskList.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Adds a Task object to a specific index in the TaskList.
     *
     * @param index Index to be inserted.
     * @param task  Task to be added.
     */
    public void addTaskToIndex(int index, Task task) {
        tasks.add(index, task);
    }

    /**
     * Deletes a Task object from the TaskList.
     *
     * @param index Index of Task object, specified by the user.
     */
    public void deleteTask(int index) {
        tasks.remove(index);
    }

    /**
     * Checks if index is within bounds of TaskList.
     *
     * @param index Index of TaskList.
     * @return True if index is within bounds of TaskList, false otherwise.
     */
    public boolean isIndexValid(int index) {
        return index < tasks.size() && index >= 0;
    }

    /**
     * Checks if TaskList is empty.
     * @return  true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
