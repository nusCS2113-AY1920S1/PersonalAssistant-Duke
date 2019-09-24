package duke.task;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of Task objects.
 */
public class TaskList implements Serializable {

    private List<Task> tasks = new ArrayList<>();

    public TaskList() {

    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Add a new Task.
     *
     * @param task the Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    public void add(Task task, int index) {
        tasks.add(index, task);
    }
    /**
     * Get a Task.
     * @param index index of the Task to get.
     * @return a Task at the index.
     */
    @JsonIgnore
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Remove a Task by index
     * @param index index of the Task to remove.
     */
    public void remove(int index) {
        tasks.remove(index);
    }

    /**
     * Remove a Task.
     *
     * @param task the Task to remove.
     */
    public void remove(Task task) {
        tasks.remove(task);
    }

    /**
     * Returns the number of Tasks in the list.
     * @return the number of Tasks in the list.
     */
    public int size() {
        return tasks.size();
    }

    public int indexOf(Task task) {
        return tasks.indexOf(task);
    }

    public void updateRecurringTasks() {
        for (Task task : tasks) {
            if (task instanceof RecurringTask) {
                ((RecurringTask) task).updateTask();
            }
        }
    }
}

