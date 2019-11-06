//@@author kkeejjuunn

package duke.models.tasks;

import duke.exceptions.DukeException;

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
     *
     * @param taskList .
     */
    public TaskManager(ArrayList<Task> taskList) {
        for (Task task : taskList) {
            taskIdMap.put(task.getId(), task);
        }
        if (!taskList.isEmpty()) {
            this.maxId = taskList.get(taskList.size() - 1).getId();
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
     * Finds all the tasks which contain the given description.
     *
     * @param description contains the description to be searched.
     * @return            an ArrayList of tasks that contain the given description.
     */
    public ArrayList<Task> getTaskByDescription(String description) {
        description = description.toLowerCase();
        ArrayList<Task> tasksWithThisDescription = new ArrayList<>();
        for (Task task : taskIdMap.values()) {
            if (task.getDescription().toLowerCase().contains(description)) {
                tasksWithThisDescription.add(task);
            }
        }
        return tasksWithThisDescription;
    }

    /**
     * It adds a Task to the list.
     *
     * @param taskToBeAdded  contains the Task to be added to the list.
     * @throws DukeException if the task is not added successfully.
     */
    public void addTask(Task taskToBeAdded) throws DukeException {
        if (taskToBeAdded.getDescription().length() < 3) {
            throw new DukeException(TaskManager.class, "The task description is too short.");
        }
        for (Task task : taskIdMap.values()) {
            if (task.getDescription().toLowerCase().equals(taskToBeAdded.getDescription().toLowerCase())) {
                throw new DukeException(TaskManager.class,
                        "The task '" + taskToBeAdded.getDescription() + "' already existed.");
            }
        }
        if (taskToBeAdded.getId() == 0) {
            maxId += 1;
            taskToBeAdded.setId(maxId);
        }
        taskIdMap.put(taskToBeAdded.getId(), taskToBeAdded);
    }

    /**
     * It removes the task with the given id of the task.
     *
     * @param id             contains the id of the Task to be deleted.
     * @throws DukeException if the task with the id does not exist.
     */
    public void deleteTask(int id) throws DukeException {
        if (taskIdMap.containsKey(id)) {
            taskIdMap.remove(id);
        } else {
            throw new DukeException(TaskManager.class, "The task with id " + id + " does not exist.");
        }

    }

    /**
     * It checks whether a task exists.
     *
     * @param  id contains the id of the task.
     * @return    true if the task exists, otherwise false.
     */
    public boolean doesExist(int id) {
        if (taskIdMap.containsKey(id)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the Task in the list with the given index.
     *
     * @param id contains the id of the Task.
     * @return   the task in the list with the specific id.
     */
    public Task getTask(int id) throws DukeException {
        if (taskIdMap.containsKey(id)) {
            return taskIdMap.get(id);
        } else {
            throw new DukeException(TaskManager.class, "The task with id " + id + " does not exist.");
        }
    }

    /**
     * It retrieves all the task in the list.
     *
     * @return the list of tasks.
     */
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskIdMap.values());
    }
}
