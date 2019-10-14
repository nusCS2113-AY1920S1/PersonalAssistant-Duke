package duke.task;

import duke.core.DukeException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a list of Task that can perform operations such as
 * add and delete on the tasks.
 */
public class TaskManager {
    private HashMap<Integer, Task> taskIdMap = new HashMap<>();
    private int maxId = 0;

    /**
     * An ArrayList structure.
     */
    private ArrayList<Task> taskList;

    /**
     * Constructor used when Duke successfully loads a TaskList from a saved file.
     * Takes loaded taskList and uses it during Duke's new session.
     * @param taskList
     */
    public TaskManager(ArrayList<Task> taskList) {
        for (Task task : taskList) {
            taskIdMap.put(task.getID(), task);
        }
        if (!taskList.isEmpty()) {
            this.maxId = taskList.get(taskList.size()-1).getID();
        }
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
     * @param task The Task to be added to the list.
     */
    public void addTask(Task task) {
        if (task.getID() == 0){
            maxId += 1;
            task.setID(maxId);
        }
        taskIdMap.put(task.getID(), task);
    }

    /**
     * Removes the Task with the given index from the list.
     *
     * //@param i The index of the Task to be deleted.
     */
    /*public void deleteTask(Integer i) throws DukeException {
        if (getSize() < i) {
            throw new DukeException("Task Number " + i + " does not exist");
        }
        taskList.remove(i - 1);
    }*/

    public boolean isExist(int id) {
        if (taskIdMap.containsKey(id)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Returns the Task in the list with the given index.
     *
     * @param id The index of the Task.
     * @return The Task in the list with the specific index.
     */
    public Task getTask(int id) throws DukeException {
        if (taskIdMap.containsKey(id)){
            return taskIdMap.get(id);
        }
        else{
            throw new DukeException("The task with id "+ id + " does not exist.");
        }
    }

    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskIdMap.values());
    }
}
