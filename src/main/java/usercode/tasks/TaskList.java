package usercode.tasks;

import exceptions.FarmioException;
import frontend.GameConsole;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {

    public TaskList() {
    }

    /**
     * Creates a TaskList Object from the JSONarray in the save file.
     *
     * @param array JSONArray to be converted into TaskList
     * @throws FarmioException if the array cannot be converted successfully
     */
    public TaskList(JSONArray array) throws FarmioException {
        for (JSONObject object : (Iterable<JSONObject>) array) {
            this.add(Task.toTask(object));
        }
    }

    /**
     * Adds a new Task object to the end of the TaskList.
     *
     * @param task the Task Object to be added
     * @throws FarmioException if the TaskList already has 18 Tasks
     */
    public void addTask(Task task) throws FarmioException {
        if (this.size() < GameConsole.FRAME_SECTION_HEIGHT) {
            this.add(task);
        } else {
            throw new FarmioException("Unable to add more task! Please optimize your code!");
        }
    }

    /**
     * Edits a Task Object in the TaskList by replacing it with the edited one.
     *
     * @param taskID the index of the Task to be edited
     * @param task the Task that replaces the current one
     * @throws FarmioException if the TaskID is invalid
     */
    public void editTask(int taskID, Task task) throws FarmioException {
        try {
            this.set(taskID - 1, task);
        } catch (IndexOutOfBoundsException e) {
            throw new FarmioException("Invalid TaskID!");
        }
    }

    /**
     * Inserts a Task at any position of the TaskList.
     *
     * @param taskID the index to insert the new Task at
     * @param task the Task to be inserted
     * @throws FarmioException if the TaskID is invalid
     */
    public void insertTask(int taskID, Task task) throws FarmioException {
        if (this.size() >= GameConsole.FRAME_SECTION_HEIGHT) {
            throw new FarmioException("Unable to add more task! Please optimize your code!");
        }
        try {
            this.add(taskID - 1, task);
        } catch (IndexOutOfBoundsException e) {
            throw new FarmioException("Invalid Insert Position!");
        }
    }

    /**
     * Converts the TaskList to a JSONarray to be saved.
     *
     * @return JSONarray that reperesents the TaskList
     */
    public JSONArray toJson() {
        JSONArray array = new JSONArray();
        for (Task task: this) {
            array.add(task.toJson());
        }
        return array;
    }

    /**
     * Converts the TaskList to a readable format with index number to be printed.
     *
     * @return String Array to be printed by the UI
     */
    public ArrayList<String> toStringArray() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < this.size(); i++) {
            list.add((i + 1) + ". " + this.get(i).toString());
        }
        return list;
    }

    /**
     * Deletes one Task in the TaskList.
     *
     * @param taskID the index of the Task to be removed
     * @return the String description of the deleted Task
     * @throws FarmioException if the TaskID is invalid
     */
    public String deleteTask(int taskID) throws FarmioException {
        try {
            Task t = this.remove(taskID - 1);
            return t.toString();
        } catch (IndexOutOfBoundsException e) {
            throw new FarmioException("Invalid TaskID!");
        }
    }

    public void deleteAll() {
        this.clear();
    }
}
