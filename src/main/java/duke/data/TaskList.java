package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            taskListBuilder.append(System.lineSeparator()).append(i + 1).append(".")
                    .append(currTask.toString());
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
        return currTask.toString();
    }

    /**
     * Adds a new task to the list.
     *
     * @param newTask The task to be added.
     * @return A success message, with the String representation of the newly added task, and a message
     *         reporting the number of tasks in the list.
     */
    public String addTask(Task newTask) {
        taskArrList.add(newTask);
        return newTask.toString();
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
        String delStr = taskArrList.get(idx).toString();
        taskArrList.remove(idx);
        return delStr;
    }

    /**
     * Concatenates the descriptions of all tasks whose names contain the searchTerm.
     *
     * @param searchTerm String to search through the tasks for.
     * @return Concatenated descriptions of matching tasks.
     */
    public String find(String searchTerm) throws DukeException {
        int i = 1;
        StringBuilder searchBuilder = new StringBuilder();
        for (Task task : taskArrList) {
            if (task.getName().contains(searchTerm)) {
                searchBuilder.append(System.lineSeparator()).append(i).append(".")
                        .append(task.toString());
                ++i;
            }
        }

        if (i == 1) {
            throw new DukeException("Can't find any matching tasks!");
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
    public int getTaskIdx(String idxStr) throws DukeException {
        if (idxStr.matches("^\\d+$")) { //if second arg is an integer
            int idx = Integer.parseInt(idxStr) - 1;
            if (idx >= 0 && idx < taskArrList.size()) {
                return idx;
            } else {
                throw new DukeException("I don't have that entry in the list!");
            }
        } else {
            throw new DukeException("You need to tell me what the number of the entry is!");
        }
    }

    /**
     * Reports the addition of a number of tasks.
     *
     * @param addStr    The descriptions of the tasks, formatted with two spaces behind each task and a leading line
     *                  separator.
     * @param taskCount Number of tasks added.
     * @return A String reporting the addition of one or more tasks.
     */
    public String getAddReport(String addStr, long taskCount) {
        addStr = ((taskCount == 1) ? "Got it, I've added this task:" + addStr :
                "Got it, I've added these " + taskCount + " tasks:" + addStr);
        return addStr + System.lineSeparator() + getTaskCountStr();
    }

    /**
     * Reports the deletion of a number of tasks.
     *
     * @param delStr    The descriptions of the tasks, formatted with two spaces behind each task and a leading line
     *                  separator.
     * @param taskCount Number of tasks added.
     * @return A String reporting the deletion of one or more tasks.
     */
    public String getDelReport(String delStr, long taskCount) {
        delStr = ((taskCount == 1) ? "Noted. I've removed this task:" + delStr :
                "Noted. I've removed these " + taskCount + " tasks:" + delStr);
        return delStr + System.lineSeparator() + getTaskCountStr();
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
     * Reports the schedule of the user on a specified date.
     *
     * @param date The specified date.
     * @return Concatenated data representations of all scheduled tasks on the specified date.
     * @throws DukeException If the user has no scheduled tasks on the specified date.
     */
    public String listSchedule(LocalDate date) throws DukeException {
        List<TimedTask> timedTaskList = new ArrayList<>();

        for (Task currTask : taskArrList) {
            // TODO: Code smell
            if (!currTask.isDone() && currTask instanceof TimedTask) {
                LocalDate taskDate = ((TimedTask) currTask).getDateTime().toLocalDate();

                if (taskDate.isEqual(date)) {
                    timedTaskList.add((TimedTask) currTask);
                }
            }
        }
        Collections.sort(timedTaskList);

        StringBuilder scheduleBuilder = new StringBuilder();
        int scheduleCount = 0;

        for (Task timedTask : timedTaskList) {
            scheduleCount = scheduleCount + 1;
            scheduleBuilder.append(System.lineSeparator()).append(scheduleCount).append(".")
                    .append(timedTask.toString());
        }

        if (scheduleCount == 0) {
            throw new DukeException("You have no tasks due on " + date.format(DateTimeFormatter.ofPattern("d/M/yyyy"))
                    + "!");
        }

        return scheduleBuilder.toString();
    }

    /**
     * Returns a string that indicates if snooze was successful.
     * @param index the tasks to be snoozed index in the list of all tasks
     * @param datetime the new time that the task will be snoozed to
     * @return message to user that states that the snooze was successful
     * @throws DukeException in the event of a invalid snooze input
     */
    public String snooze(int index, LocalDateTime datetime) throws DukeException {
        taskArrList.get(index).changeTime(datetime);
        return "The task have been snoozed;\n\t" + taskArrList.get(index);
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
        return currTask.toString();
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

            if (currReminder != null && currReminder.getDateTime().isBefore(LocalDateTime.now())) {
                reminderCount = reminderCount + 1;
                reminderListBuilder.append(System.lineSeparator()).append(reminderCount).append(".")
                        .append(currTask.toString());
            }
        }

        if (reminderCount == 0) {
            throw new DukeException("You have no reminders.");
        }

        return reminderListBuilder.toString();
    }
}
