package duke.core;

import duke.task.Task;

import java.util.ArrayList;
/**
 * Represents a list of Task that can perform operations such as
 * add and delete on the tasks.
 */
public class TaskList  {
    /**
     * An ArrayList structure.
     */
    private ArrayList<Task> taskList;

    /**
     * instantiate a new TaskList with a empty list.
     */
    public TaskList(ArrayList<Task> task) {
        this.taskList = task;
    }
    /**
     * Retrieve the entire task list stored inside the ArrayList.
     */
    public ArrayList<Task> fullTaskList(){
        return taskList;
    }
    /**
     * Adds a Task to the list.
     * @param t The Task to be added to the list.
     */
    public void addTask(Task t) {
        taskList.add(t);
    }
    /**
     * Removes the Task with the given index from the list.
     * @param i The index of the Task to be deleted.
     */
    public void deleteTask(Integer i) throws IndexOutOfBoundsException {
            taskList.remove(i - 1);
    }
    /**
     * Returns the Task in the list with the given index.
     * @param i The index of the Task.
     * @return The Task in the list with the specific index.
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
