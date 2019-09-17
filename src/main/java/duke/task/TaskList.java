package duke.task;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskList {
    // TSV files will have one entry per line, tabs disallowed in input

    private ArrayList<Task> taskArrList;

    /**
     * Creates a new TaskList, loading data from the Storage object provided.
     *
     * @param storage The Storage object pointing to the TSV file containing the data to load.
     * @throws DukeResetException If file is corrupted or the data has been edited to be unreadable.
     * @throws DukeFatalException If unable to write data file.
     */
    public TaskList(Storage storage) throws DukeResetException, DukeFatalException {
        taskArrList = storage.parseTaskFile();
    }

    /**
     * Creates a new, empty TaskList.
     */
    public TaskList() {
        taskArrList = new ArrayList<>();
    }

    /**
     * Concatenates the string representation of each task, numbering them from first added to last, and returns this
     * list as a String.
     *
     * @return String representation of all tasks, numbered chronologically.
     * @throws DukeException If there are no tasks yet.
     */
    public String listTasks() throws DukeException {
        int taskCount = taskArrList.size();
        if (taskCount == 0) {
            throw new DukeException("You don't have any tasks yet!");
        }
        StringBuilder taskListBuilder = new StringBuilder();
        for (int i = 0; i < taskCount; ++i) {
            Task currTask = taskArrList.get(i);
            taskListBuilder.append(System.lineSeparator()).append(i + 1).append(".").append(currTask.toString());
        }
        return taskListBuilder.toString();
    }

    /**
     * Concatenates the data representation of each task, for writing to the data file.
     *
     * @return Concatenated data representations of all tasks.
     */
    public String getFileStr() {
        StringBuilder fileStrBuilder = new StringBuilder();
        for (Task task : taskArrList) {
            fileStrBuilder.append(task.toData()).append(System.lineSeparator());
        }
        return fileStrBuilder.toString();
    }

    /**
     * Marks a task as in the list as done.
     *
     * @param idxStr The argument given by the user to identify the task to be marked done.
     * @return A success message with the String representation of the newly completed task.
     * @throws DukeException If idxStr cannot be resolved to a valid task index.
     */
    public String markDone(String idxStr) throws DukeException {
        Task currTask = taskArrList.get(getTaskIdx(idxStr));
        currTask.markDone();
        return "Nice! I've marked this task as done:" + System.lineSeparator()
                + "  " + currTask.toString();
    }

    /**
     * Adds a new task to the list.
     *
     * @param newTask The task to be added.
     * @return A success message, with the String representation of the newly added task, and a message
     *         reporting the number of tasks in the list.
     */
    public String addTask(Task newTask) {
        String addStr = "Got it, I've added this task:" + System.lineSeparator()
                + "  " + newTask.toString() + System.lineSeparator();
        taskArrList.add(newTask);
        return addStr + getTaskCountStr();
    }

    /**
     * Deletes a task from the list.
     *
     * @param idxStr The argument given by the user to identify the task to be deleted.
     * @return A success message with the String representation of the newly deleted task, and a message reporting the
     *         number of tasks in the list.
     * @throws DukeException If idxStr cannot be resolved to a valid task index.
     */
    public String deleteTask(String idxStr) throws DukeException {
        int idx = getTaskIdx(idxStr);
        String delString = "Noted. I've removed this task:" + System.lineSeparator()
                + "  " + taskArrList.get(idx).toString();
        taskArrList.remove(idx);
        return delString + System.lineSeparator() + getTaskCountStr();
    }

    /**
     * Concatenates the descriptions of all tasks whose names contain the searchTerm.
     *
     * @param searchTerm String to search through the tasks for.
     * @return Concatenated descriptions of matching tasks.
     */
    public String find(String searchTerm) {
        int i = 1;
        StringBuilder searchBuilder = new StringBuilder();
        searchBuilder.append("Here are the tasks that contain '").append(searchTerm).append("':");
        for (Task task : taskArrList) {
            if (task.getName().contains(searchTerm)) {
                searchBuilder.append(System.lineSeparator()).append(i).append(".").append(task.toString());
                ++i;
            }
        }

        if (i == 1) {
            return "Can't find any matching tasks!";
        } else {
            return searchBuilder.toString();
        }
    }

    /**
     * Parses a String to extract an integer, and checks if this integer is a valid index for the list.
     *
     * @param idxStr A String representing an integer, without leading/trailing spaces
     * @return An integer that is a valid index for an entry on the list
     * @throws DukeException If idxStr cannot be parsed, or the integer is not a valid index
     */
    private int getTaskIdx(String idxStr) throws DukeException {
        if (idxStr.matches("^\\d+$")) { //if second arg is an integer
            int idx = Integer.parseInt(idxStr) - 1;
            if (idx < taskArrList.size()) {
                return idx;
            } else {
                throw new DukeException("I don't have that entry in the list!");
            }
        } else {
            throw new DukeException("You need to tell me what the number of the entry is!");
        }
    }

    /**
     * Reports the number of tasks currently in the list.
     *
     * @return A String reporting the current number of tasks.
     */
    private String getTaskCountStr() {
        int taskCount = taskArrList.size();
        String taskCountStr = taskCount + ((taskCount == 1) ? " task" : " tasks");
        return "Now you have " + taskCountStr + " in the list.";
    }

    /**
     * Sets a reminder for a task in the list.
     *
     * @param idxStr   The argument given by the user to identify the task.
     * @param reminder The reminder to set for the task.
     * @return A success message with the String representation of the newly added reminder.
     * @throws DukeException If idxStr cannot be resolved to a valid task index.
     */
    public String setReminder(String idxStr, Reminder reminder) throws DukeException {
        Task currTask = taskArrList.get(getTaskIdx(idxStr));
        currTask.setReminder(reminder);
        return "Roger! I've set a reminder for this task." + System.lineSeparator()
                + "  " + currTask.toString();
    }

    /**
     * Concatenates the string representation of each reminder, and returns this list as a String.
     *
     * @return String representation of all reminders, numbered chronologically.
     */
    public String listReminders() throws DukeException {
        StringBuilder reminderListBuilder = new StringBuilder();

        int reminderCount = 0;
        for (Task currTask : taskArrList) {
            Reminder currReminder = currTask.getReminder();

            if (currReminder != null) {
                if (currReminder.getDatetime().isBefore(LocalDateTime.now())) {
                    reminderCount = reminderCount + 1;
                    reminderListBuilder.append(System.lineSeparator()).append(reminderCount).append(".")
                            .append(currTask.toString());
                }
            }
        }

        if (reminderCount == 0) {
            throw new DukeException("You have no reminders.");
        }

        return reminderListBuilder.toString();
    }
}
