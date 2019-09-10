package duke;

import duke.task.Task;

import java.util.ArrayList;

/**
 * Represents a list of <code>Task</code> objects.
 */
public class TaskList {

    protected ArrayList<Task> arr;

    /**
     * Constructor for <code>TaskList</code>.
     * @param arr <code>TaskList</code> that contains <code>Task</code> objects.
     */
    public TaskList(ArrayList<Task> arr) {
        this.arr = arr;
    }

    /**
     * Constructor for <code>TaskList</code>.
     */
    public TaskList() {
        this.arr = new ArrayList<Task>();
    }

    /**
     * Retrieves all the <code>Task</code> objects in <code>TaskList</code>.
     * @return <code>TaskList</code> containing all its <code>Task</code> objects.
     */
    public ArrayList<Task> getTasks() {
        return arr;
    }

    /**
     * Retrieves the number of <code>Task</code> objects in the <code>TaskList</code>.
     * @return Number of <code>Task</code> objects in the <code>TaskList</code>.
     */
    public int getSize() {
        return arr.size();
    }

    /**
     * Retrieves the <code>Task</code> object at a particular index.
     * @param index Index of <code>Task</code> object, specified by the user.
     * @return <code>Task</code> object at a particular index.
     */
    public Task getTask(int index) {
        return arr.get(index);
    }

    /**
     * Adds a <code>Task</code> object to the <code>TaskList</code>.
     * @param task <code>Task</code> object to be added to <code>TaskList</code>.
     */
    public void addTask(Task task) {
        arr.add(task);
    }

    /**
     * Deletes a <code>Task</code> object from the <code>TaskList</code>.
     * @param index Index of <code>Task</code> object, specified by the user
     */
    public void deleteTask(int index) {
        arr.remove(index);
    }
}
