package duke.task;

import duke.calendarMap.DateMap;
import duke.exception.DukeTaskClashException;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {
    private DateMap dateMap = new DateMap();

    /**
     * Get the task for index based on 1.
     * @param index the index of task to be queried, index starts from 1
     * @return The task with index starting from 0
     */
    public Task getTask(int index) {
        return this.get(index - 1);
    }

    /**
     * Delete task based on user input.
     * @param index the index of task to be deleted, index starts from 1
     * @return The task that is deleted
     */
    public Task deleteTask(int index) {
        deleteFromMap(this.getTask(index));
        return this.remove(index - 1);
    }

    /**
     * Check if task is mark as done.
     * @param index The index of task to check if it is already completed, index starts from 1
     * @return {@code true} The task has been marked as completed
     *          {@code false} The task has not been mark as completed
     */
    public boolean isCompletedTask(int index) {
        return getTask(index).isCompleted();
    }

    /**
     * Set task as done based on user input.
     * @param index the index of task to be set as done, index starts from 1
     * @return The task that is completed and set as done
     */
    public Task doneTask(int index) {
        Task refTask = this.getTask(index);
        refTask.markAsDone();

        return refTask;
    }

    /**
     * Finds all the task that contains the String and append task to new ArrayList.
     * @param item String that contains the item user wants to find
     * @return New ArrayList with task that contains the String user input
     */
    public TaskList findTask(String item) {
        TaskList foundTask = new TaskList();
        for (Task i : this) {
            if (i.getDescription().contains(item)) {
                foundTask.add(i);
            }
        }

        return foundTask;
    }

    public boolean addTask(Task task) throws DukeTaskClashException {
        addToMap(task);
        return super.add(task);
    }

    private void addToMap(Task task) throws DukeTaskClashException {
        if (task.getSymbol().equals("[E]") || task.getSymbol().equals("[D]")) {
            dateMap.addTask(task.getDateTime(), task);
        }
    }

    private void deleteFromMap(Task task) {
        if (task.getSymbol().equals("[E]") || task.getSymbol().equals("[D]")) {
            dateMap.deleteTask(task.getDateTime(), task);
        }
    }

    public String viewSchedule(String date) {
        return dateMap.viewSchedule(date);
    }
}
