package com.nwjbrandon.duke.services.task;

import com.nwjbrandon.duke.exceptions.DukeOutOfBoundException;
import com.nwjbrandon.duke.services.ui.Terminal;

import java.util.ArrayList;

public class TaskList {

    /**
     * Container for tasks.
     */
    private ArrayList<Task> tasksList;

    /**
     * Create list of tasks.
     */
    public TaskList() {
        tasksList = new ArrayList<>();
    }

    /**
     * Get the list of tasks.
     * @return list of tasks.
     */
    public ArrayList<Task> getTasksList() {
        return tasksList;
    }

    /**
     * Add task.
     * @param task task.
     */
    public void addTask(Task task) {
        tasksList.add(task);
    }

    /**
     * Remove task.
     * @param taskIndex index of task.
     */
    public void removeTask(int taskIndex) {
        tasksList.get(taskIndex).removeTaskString(this.numberOfTasks());
        tasksList.remove(taskIndex);

    }

    /**
     * Set task as done.
     * @param taskIndex index of task.
     */
    public void markDone(int taskIndex) {
        tasksList.get(taskIndex).setDoneStatus(true);
    }

    /**
     * Get number of tasks.
     * @return number of tasks.
     */
    public int numberOfTasks() {
        return tasksList.size();
    }

    /**
     * Get task.
     * @param taskIndex task index.
     * @return task.
     */
    public Task getTask(int taskIndex) {
        return tasksList.get(taskIndex);
    }

    /**
     * Get tasks by keywords.
     * @param keyword keyword by input.
     */
    public void searchTask(String keyword) {
        Terminal.showSearchTask(this, keyword);
    }

    /**
     * Show all tasks.
     */
    public void showAll() {
        Terminal.showTasksList(this);
    }

}
