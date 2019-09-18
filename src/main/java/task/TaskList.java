package task;

import exception.DukeException;

import java.util.ArrayList;

/**
 * Represents the list of various tasks stored by Duke.
 * The list of tasks can be both modified and read.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs a taskList with default size 100.
     */
    public TaskList() {
        tasks = new ArrayList<Task>(100);
    }

    /**
     * Constructs a taskList with list of strings of existing tasks from the storage.
     *
     * @param storageStrings List of strings loaded from the storage.
     */
    public TaskList(ArrayList<String> storageStrings) {
        tasks = new ArrayList<Task>(100);
        for (String storageString : storageStrings) {
            String[] splitStorageString = storageString.split("\\s+\\Q|\\E\\s+");

            switch (splitStorageString[0]) {
            case "T":
                tasks.add(new ToDo(splitStorageString));
                break;
            case "D":
                tasks.add(new Deadline(splitStorageString));
                break;
            case "E":
                tasks.add(new Event(splitStorageString));
                break;
            default:
                throw new DukeException("Invalid task in storage!");
            }
        }
    }

    /**
     * Adds a task into the taskList.
     *
     * @param task task to be added into the taskList.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Marks the task with the given index as done.
     *
     * @param index The index of task in taskList.
     */
    public void done(int index) { // 0-based
        tasks.get(index).markAsDone();
    }

    /**
     * Deletes the task with the given index from the taskList.
     *
     * @param index The index of the task in taskList.
     */
    public void delete(int index) { // 0-based
        tasks.remove(index);
    }

    /**
     * Reschedules the deadline with the given index in taskList.
     *
     * @param index The index of the deadline to be scheduled
     * @param ddl The new due <code>Date</code>.
     */
    public void reschedule(int index, String ddl) throws DukeException {
        try {
            ((Deadline) tasks.get(index)).reschedule(ddl);
        } catch (ClassCastException e) {
            throw new DukeException("☹ OOPS!!! Use /start and /end instead.");
        }
    }

    /**
     * Reschedules the event with the given index in taskList.
     *
     * @param index The index of the event to be scheduled.
     * @param start The new start <code>Date</code>.
     * @param end The new end <code>Date</code>.
     */
    public void reschedule(int index, String start, String end) throws DukeException  {
        try {
            ((Event) tasks.get(index)).reschedule(start, end);
        } catch (ClassCastException e) {
            throw new DukeException("☹ OOPS!!! Use /by instead.");
        }
    }

    /**
     * Returns the collection of index of all tasks found relevant to the searched keyword.
     *
     * @param s The searched keyword.
     * @return Collection of index of all tasks found relevant to the searched keyword.
     */
    public ArrayList<Integer> find(String s) {
        ArrayList<Integer> matchedList = new ArrayList<Integer>(100);
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).contains(s)) {
                matchedList.add(i);
            }
        }
        return matchedList;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the information of the task with given index to be printed by UI.
     *
     * @param index Index of a task in taskList.
     * @return Information of a task to be printed by UI.
     */
    public String getTaskInfo(int index){
        return tasks.get(index).toString();
    }

    /**
     * Returns the list of all tasks' information(<code>String</code>)
     * in the taskList following the storage format.
     * This list of strings are used to update the storage of Duke.
     *
     * @return List of all tasks' information in storage format.
     */
    public ArrayList<String> toStorageStrings() {
        ArrayList<String> taskStrings = new ArrayList<String>(100);
        for (Task task : tasks) {
            taskStrings.add(task.toStorageString());
        }
        return taskStrings;
    }

    /**
     * Returns the size of taskList.
     *
     * @return The size of taskList.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Returns true or false depending if the task is done.
     *
     * @return True or false.
     */
    public boolean isDone(int index) {
        return tasks.get(index).isDone;
    }
}