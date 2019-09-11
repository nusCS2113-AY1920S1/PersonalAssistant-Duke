package duke.core;

import duke.task.Task;

import java.util.ArrayList;
/**
 * Represents a list of <code>duke.task.Task</code> that can perform operations such as
 * add and delete on the tasks.
 */
public class TaskList  {
    /**
     * An <code>ArrayList</code> structure.
     */
    private ArrayList<Task> taskList;

    /**
     * instantiate a new <code>duke.core.TaskList</code> with a empty list.
     */
    public TaskList(ArrayList<Task> task) {
        this.taskList = task;
    }
    /**
     * Retrieve the entire task list stored inside the <code>ArrayList</code>.
     */
    public ArrayList<Task> fullTaskList(){
        return taskList;
    }
    /**
     * Adds a <code>duke.task.Task</code> to the list.
     * @param t The <code>duke.task.Task</code> to be added to the list.
     */
    public void addTask(Task t) {
        taskList.add(t);
    }
    /**
     * Removes the <code>duke.task.Task</code> with the given index from the list.
     * @param i The index of the <code>duke.task.Task</code> to be deleted.
     */
    public void deleteTask(Integer i) throws IndexOutOfBoundsException {
            taskList.remove(i - 1);
    }
    /**
     * Returns the <code>duke.task.Task</code> in the list with the given index.
     * @param i The index of the <code>duke.task.Task</code>.
     * @return The <code>duke.task.Task</code> in the list with the specific index.
     */
    public Task getTask(int i) throws IndexOutOfBoundsException {
        return taskList.get(i - 1);
    }
    /**
     * Returns the size of task list.
     * @return An integer representing the number of tasks in the list.
     */
    public int getSize() {
        return taskList.size();
    }

    public TaskList(){
        taskList = new ArrayList<Task>();
    }

}
