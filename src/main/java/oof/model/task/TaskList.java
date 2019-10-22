package oof.model.task;

import oof.model.task.Task;

import java.util.ArrayList;

/**
 * Represents a list of Task objects.
 */
public class TaskList {

    protected ArrayList<Task> taskList;

    /**
     * Constructor for TaskList.
     *
     * @param taskList TaskList that contains Task objects.
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Constructor for TaskList.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Retrieves all the Task objects in TaskList.
     *
     * @return TaskList containing all its Task objects.
     */
    public ArrayList<Task> getTasks() {
        return taskList;
    }

    /**
     * Retrieves the number of Task objects in the TaskList.
     *
     * @return Number of Task objects in the TaskList.
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Retrieves the Task object at a particular index.
     *
     * @param index Index of Task object, specified by the user.
     * @return Task object at a particular index.
     */
    public Task getTask(int index) {
        return taskList.get(index);
    }

    /**
     * Adds a Task object to the TaskList.
     *
     * @param task Task object to be added to TaskList.
     */
    public void addTask(Task task) {
        taskList.add(task);
    }

    /**
     * Adds a Task object to a specific index in the TaskList.
     *
     * @param index Index to be inserted.
     * @param task  Task to be added.
     */
    public void addTaskToIndex(int index, Task task) {
        taskList.add(index, task);
    }

    /**
     * Deletes a Task object from the TaskList.
     *
     * @param index Index of Task object, specified by the user.
     */
    public void deleteTask(int index) {
        taskList.remove(index);
    }

    /**
     * Checks if index is within bounds of TaskList.
     *
     * @param index Index of TaskList.
     * @return True if index is within bounds of TaskList, false otherwise.
     */
    public boolean isIndexValid(int index) {
        return index < this.getSize() && index >= 0;
    }
}
