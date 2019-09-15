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
     * Constructs a taskList with default size 100/
     */
    public TaskList() {
        tasks = new ArrayList<Task>(100);
    }

    /**
     * Constructs a taskList with list of strings of existing tasks from the storage.
     *
     * @param taskStrings List of strings loaded from the storage.
     */
    public TaskList(ArrayList<String> taskStrings) {
        tasks = new ArrayList<Task>(100);
        for (String line : taskStrings) {
            String[] tokens = line.split("\\Q|\\E");
            for(int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
            }
            switch (tokens[0]) {
                case "T":
                    addToDo(tokens[2]);
                    if(tokens[1].equals("1")) {
                        done(tasks.size() - 1);
                    }
                    break;
                case "D":
                    addDeadline(tokens[2], tokens[3]);
                    if(tokens[1].equals("1")) {
                        done(tasks.size() - 1);
                    }
                    break;
                case "E":
                    addEvent(tokens[2], tokens[3]);
                    if(tokens[1].equals("1")) {
                        done(tasks.size() - 1);
                    }
                    break;
            }
        }
    }

    /**
     * Adds a task with description into the taskList.
     *
     * @param description Description of the added task.
     */
    public void addToDo(String description) {
        tasks.add(new ToDo(description));
    }

    /**
     * Adds a task with description and due time into the taskList.
     *
     * @param description Description of the added task.
     * @param ddl Due of the task.
     * @throws DukeException If an exception is thrown when constructing the new task.
     */
    public void addDeadline(String description, String ddl) throws DukeException{
            tasks.add(new Deadline(description, ddl));
    }

    /**
     * Adds an event with description, start and end time into the taskList.
     *
     * @param description Description of the added event.
     * @param timePiece Start and end time of the event.
     * @throws DukeException If an exception is thrown when constructing the new event.
     */
    public void addEvent(String description, String timePiece) throws DukeException {
            tasks.add(new Event(description, timePiece));
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
     * Returns the collection of index of all tasks found relevant to the searched keyword.
     *
     * @param s The searched keyword.
     * @return Collection of index of all tasks found relevant to the searched keyword.
     */
    public ArrayList<Integer> find(String s) {
        ArrayList<Integer> matchedList = new ArrayList<Integer>(100);
        for (int i = 0 ; i < tasks.size(); i++) {
            if (tasks.get(i).contains(s)) {
                matchedList.add(i);
            }
        }
        return matchedList;
    }

    /**
     * Returns the information of the task with given index to be printed by UI.
     *
     * @param index Index of a task in taskList.
     * @return Information of a task to be printed by UI.
     */
    public String getTaskInfo(int index) {
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
        for(Task task : tasks) {
            taskStrings.add(task.toStorageString());
        }
        return taskStrings;
    }

    /**
     * Returns the size of taskList.
     *
     * @return The size of taskList.
     */
    public int getSize(){
        return tasks.size();
    }
}