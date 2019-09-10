package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A class that holds a list of tasks that may be added to, removed or
 * marked as done. This list is indexed starting from 1.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Initializes an empty task list object that every command will act on.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Imports the given ArrayList of Tasks and initializes this instance with them to reload saved state.
     *
     * @param stringTasks A list of tasks in human readable exported format to be imported.
     */
    public TaskList(ArrayList<String> stringTasks) {
        tasks = new ArrayList<>();
        for (String line : stringTasks) {
            String taskType = line.substring(0, 1);
            String taskStatus = line.substring(4, 5);
            String[] lineSplit = line.split("\\|", -1);
            int descriptionLength = Integer.parseInt(lineSplit[2].trim());
            int descriptionLengthIndex = line.indexOf(" | ", line.indexOf(" ") + 1);
            int descriptionLengthLength = lineSplit[2].length();
            int startOfDescriptionIndex = descriptionLengthIndex + 1 + descriptionLengthLength + 3;
            String description = line.substring(startOfDescriptionIndex,
                    startOfDescriptionIndex + descriptionLength);
            switch (taskType) {
            case "T":
                Todo todo = new Todo(description);
                if ("1".equals(taskStatus)) {
                    todo.markDone();
                }
                tasks.add(todo);
                break;
            case "D":
                String date = line.substring(startOfDescriptionIndex + descriptionLength, line.length());
                String[] dateSplit = date.split("\\|", -1);
                int dateLength = Integer.parseInt(dateSplit[1].trim());
                int dateLengthLength = dateSplit[1].length();
                int startOfDateIndex = 2 + dateLengthLength + 2;
                String dateString = date.substring(startOfDateIndex, date.length());
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                LocalDateTime by = LocalDateTime.parse(dateString, inputFormatter);
                Deadline deadline = new Deadline(description, by);
                if ("1".equals(taskStatus)) {
                    deadline.markDone();
                }
                tasks.add(deadline);
                break;

            case "E":
                date = line.substring(startOfDescriptionIndex + descriptionLength, line.length());
                dateSplit = date.split("\\|", -1);
                dateLength = Integer.parseInt(dateSplit[1].trim());
                dateLengthLength = dateSplit[1].length();
                startOfDateIndex = 2 + dateLengthLength + 2;
                dateString = date.substring(startOfDateIndex, date.length());
                inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                LocalDateTime at = LocalDateTime.parse(dateString, inputFormatter);
                Event event = new Event(description, at);
                if ("1".equals(taskStatus)) {
                    event.markDone();
                }
                tasks.add(event);
                break;
            }
        }
    }

    /**
     * Returns the number of tasks in this list.
     *
     * @return The size of this instance's list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a new undone task to the end of the list.
     *
     * @param task The task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Gets the task at one-indexed position i in the list.
     *
     * <code>get()</code>
     * takes an index i starting from 1, raising an exception if that index is invalid.
     *
     * @param i The index of the task to be retrieved, starting from 1.
     * @return The undone new task.
     * @throws IndexOutOfBoundsException If position i is not in the list.
     */
    public Task get(int i) throws IndexOutOfBoundsException {
        return tasks.get(i - 1);
    }

    /**
     * Filters this instance's list for those tasks matching a given keyword.
     *
     * @param keyword The case insensitive keyword being searched for.
     * @return A smaller or same-size ArrayList containing those tasks with the given keyword.
     */
    public ArrayList<Task> filter(String keyword) {
        ArrayList<Task> output = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                output.add(task);
            }
        }
        return output;
    }

    /**
     * Marks the task at one-indexed position i in the list as done.
     *
     * <code>markDone()</code>
     * takes an index i starting from 1, raising an exception if that index is invalid.
     *
     * @param i The index of the task to be retrieved, starting from 1.
     * @throws IndexOutOfBoundsException If position i is not in the list.
     */
    public void markDone(int i) throws IndexOutOfBoundsException {
        tasks.get(i - 1).markDone();
    }

    /**
     * Deletes the task at one-indexed position i in the list.
     *
     * <code>delete()</code>
     * takes an index i starting from 1, raising an exception if that index is invalid.
     *
     * @param i The index of the task to be retrieved, starting from 1.
     * @throws IndexOutOfBoundsException If position i is not in the list.
     */
    public void delete(int i) throws IndexOutOfBoundsException {
        tasks.remove(i - 1);
    }

    /**
     * Exports the tasks in this list into a human readable ArrayList of strings
     * which can then be written to disk.
     *
     * @return An ArrayList of strings representing this list's tasks.
     */
    public ArrayList<String> export() {
        ArrayList<String> output = new ArrayList<>();
        for (int i = 1; i <= tasks.size(); i++) {
            output.add(tasks.get(i - 1).export());
        }
        return output;
    }
}
