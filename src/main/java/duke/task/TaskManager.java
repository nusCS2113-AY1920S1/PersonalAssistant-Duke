package duke.task;

import java.util.ArrayList;

/**
 * Represents a list of Task that can perform operations such as
 * add and delete on the tasks.
 */
public class TaskManager {
    /**
     * An ArrayList structure.
     */
    private ArrayList<Task> taskList;

    /**
     * Constructor used when Duke successfully loads a TaskList from a saved file.
     * Takes loaded taskList and uses it during Duke's new session.
     * @param loadedTaskList
     */
    public TaskManager(ArrayList<Task> loadedTaskList) {
        this.taskList = loadedTaskList;
    }

    /**
     * Constructor used when Duke cannot successfully load a TaskList from a saved file.
     * Instantiates a new TaskList with an empty list.
     */
    public TaskManager() {
        this.taskList = new ArrayList<Task>();
    }

    /**
     * Adds a Task to the list.
     *
     * @param t The Task to be added to the list.
     */
    public void addTask(Task t) {
        taskList.add(t);
    }

    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList);
    }

}
