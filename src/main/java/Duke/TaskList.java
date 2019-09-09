package Duke;



import Duke.Tasks.Task;

import java.util.ArrayList;

public class TaskList{
    private ArrayList<Task> data;

    /**
     * Initialises data from current taskList being passed
     * @param taskList the existing taskList loaded from save file
     */
    public TaskList(ArrayList<Task> taskList) {
        data = new ArrayList<>(taskList);
    }

    /**
     * Creates a new arrayList for the taskList
     */
    public TaskList() {
        this.data = new ArrayList<>();
    }

    /**
     * Method to get the Task from index 'position'
     * @param position Index of task in taskList
     * @return Task at index 'position'
     * @throws DukeException Shows error when bounds are exceeded
     */
    public Task get(int position) throws DukeException {
        if (position >= data.size() || position < 0)
            throw new DukeException("Out of bounds of data!");
        return data.get(position);
    }

    /**
     * Method to remove the Task from index 'position'
     * @param position Index of task in taskList
     * @throws DukeException Shows error when bounds are exceeded
     */
    public void remove(int position) throws DukeException {
        if (position >= data.size() || position < 0)
            throw new DukeException("Task is not within list size!");
        data.remove(position);
    }

    /**
     * Method to get the current size of data
     * @return Size of current data
     */
    public int size() {
        return data.size();
    }

    /**
     * Method to get the taskList
     * @return ArrayList of current tasks
     */
    public ArrayList<Task> getData() {
        return data;
    }
}
