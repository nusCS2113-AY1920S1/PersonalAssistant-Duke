package oof.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

import oof.model.task.Deadline;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;
import oof.ui.Ui;

/**
 * Represents a Reminder class to remind user on upcoming deadlines.
 */
public class Reminder {

    private static final long MILLISECOND_TO_HOUR = 60 * 60 * 1000;
    private static final String DEFAULT_DATETIME = "00-00-0000 00:00";

    /**
     * Checks Task objects dates to determine if it is due soon.
     *
     * @param taskList TaskList that contains Task objects.
     * @param ui       Ui that is responsible for visual feedback.
     */
    public void checkDeadline(TaskList taskList, Ui ui, StorageManager storageManager) {
        int count = 1;
        int upcomingThreshold = storageManager.readThreshold();
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            if (task instanceof Deadline) {
                Date dueDate = parseDateTime(((Deadline) task));
                count = displayReminders(taskList, ui, dueDate, upcomingThreshold, count, i);
            }
            if (isNoDeadlineReminded(i, taskList, count)) {
                ui.printNoDeadlines();
            }
        }
    }

    /**
     * Checks if deadline is within the threshold.
     *
     * @param dueDate Due date of current deadline.
     * @param now     Current time.
     * @return true if due date of current deadline is within threshold.
     */
    private boolean isUpcoming(Date dueDate, Date now, int upcomingThreshold) {
        long diff = dueDate.getTime() - now.getTime(); // difference in time in milliseconds
        long diffHours = diff / MILLISECOND_TO_HOUR;
        return diffHours < upcomingThreshold && diffHours > 0;
    }

    /**
     * Checks if there are no deadlines being reminded.
     *
     * @param index    Index of the task to be checked.
     * @param taskList Instance of TaskList that stores Task objects.
     * @param count    Count of the deadlines being reminded
     * @return true if there are no deadlines being reminded, false otherwise.
     */
    private boolean isNoDeadlineReminded(int index, TaskList taskList, int count) {
        return (index == taskList.getSize() - 1 && count == 1);
    }

    /**
     * Parses the timestamp for the deadlines.
     *
     * @param task Deadline task object.
     * @return Returns the parsed date if the date format is parsable.
     */
    private Date parseDateTime(Deadline task) {
        Date defaultDate = new Date();
        try {
            String dateTime = task.getDeadlineDateTime();
            defaultDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(DEFAULT_DATETIME);
            return new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(dateTime);
        } catch (ParseException | DateTimeException e) {
            return defaultDate;
        }
    }

    /**
     * Displays the reminders and returns the number of reminders displayed.
     *
     * @param taskList          Instance of TaskList that stores Task objects.
     * @param ui                Ui that is responsible for visual feedback.
     * @param dueDate           Due date of current deadline.
     * @param upcomingThreshold DateTime threshold for OOF to display the reminders.
     * @param count             Number of reminders displayed thus far.
     * @param index             Index in the taskList.
     * @return Returns the updated number of reminders displayed.
     */
    private int displayReminders(TaskList taskList, Ui ui, Date dueDate, int upcomingThreshold, int count,
                                 int index) {
        Date now = new Date();
        if (isUpcoming(dueDate, now, upcomingThreshold)) {
            if (isFirstReminder(count)) {
                ui.printReminder();
            }
            ui.printUpcomingDeadline(count, taskList.getTask(index));
            count++;
        }
        return count;
    }

    /**
     * Checks if it is the first deadline being reminded.
     *
     * @param count Count of the deadlines being reminded
     * @return true if current deadline is indeed the first deadline being reminded, false otheriwse
     */
    private boolean isFirstReminder(int count) {
        return count == 1;
    }

}
