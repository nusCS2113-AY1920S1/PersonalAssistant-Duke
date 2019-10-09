package duke.task;

import duke.core.DukeException;
import java.util.ArrayList;

/**
 * Represents a list of Task that can perform operations such as
 * add and delete on the tasks.
 */
public class TaskList {
    /**
     * An ArrayList structure.
     */
    private ArrayList<Task> taskList;

    /**
     * Constructor used when Duke successfully loads a TaskList from a saved file.
     * Takes loaded taskList and uses it during Duke's new session.
     * @param loadedTaskList
     */
    public TaskList(ArrayList<Task> loadedTaskList) {
        this.taskList = loadedTaskList;
    }

    /**
     * Constructor used when Duke cannot successfully load a TaskList from a saved file.
     * Instantiates a new TaskList with an empty list.
     */
    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    /**
     * Retrieves the entire task list stored inside the ArrayList.
     */
    public ArrayList<Task> fullTaskList() {
        return taskList;
    }

    /**
     * Adds a Task to the list.
     *
     * @param t The Task to be added to the list.
     */
    public void addTask(Task t) {
        taskList.add(t);
    }

    /**
     * Removes the Task with the given index from the list.
     *
     * @param i The index of the Task to be deleted.
     */
    public void deleteTask(Integer i) throws DukeException {
        if (getSize() < i) {
            throw new DukeException("Task Number " + i + " does not exist");
        }
        taskList.remove(i - 1);
    }

    /**
     * Returns the Task in the list with the given index.
     *
     * @param i The index of the Task.
     * @return The Task in the list with the specific index.
     */
    public Task getTask(int i) throws DukeException {
        if (getSize() < i) {
            throw new DukeException("Task Number " + i + " does not exist");
        }
        return taskList.get(i - 1);
    }

    /**
     * Returns the size of task list.
     *
     * @return An integer representing the number of tasks in the list.
     */
    public int getSize() {
        return taskList.size();
    }

    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList);
    }

}
