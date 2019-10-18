package oof;


import oof.exception.OofException;
import oof.task.Deadline;
import oof.task.Task;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

/**
 * Represents a Reminder class to remind user on upcoming deadlines.
 */
public class Reminder {

    private static final int INDEX_DATE = 1;
    private static final long MILLISECOND_TO_HOUR = 60 * 60 * 1000;

    /**
     * Checks Task objects dates to determine if it is due soon.
     *
     * @param taskList TaskList that contains Task objects.
     * @param ui       Ui that is responsible for visual feedback.
     */
    public void checkDeadline(TaskList taskList, Ui ui, Storage storage) {
        int count = 1;
        int upcomingThreshold = storage.readThreshold();
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            if (task instanceof Deadline) {
                String[] lineSplit = task.toString().split("by: ");
                String result = lineSplit[INDEX_DATE].substring(0, lineSplit[INDEX_DATE].length() - 1);
                result = result.trim();
                try {
                    Date dueDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(result);
                    Date now = new Date();
                    if (isUpcoming(dueDate, now, upcomingThreshold)) {
                        if (isFirstReminder(count)) {
                            ui.printReminder();
                            ui.printUpcomingDeadline(count, taskList.getTask(i));
                            count++;
                        } else {
                            ui.printUpcomingDeadline(count, taskList.getTask(i));
                            count++;
                        }
                    }
                } catch (ParseException | DateTimeException e) {
                    System.out.println("Timestamp given is invalid! Please try again.");
                }
            }
            if (isNoDeadlineReminded(i, taskList, count)) {
                ui.printNoDeadlines();
            }
        }
        ui.printLine();
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
     * @param index Index of the task to be checked.
     * @param taskList Instance of TaskList that stores Task objects.
     * @param count Count of the deadlines being reminded
     * @return true if there are no deadlines being reminded, false otherwise.
     */
    private boolean isNoDeadlineReminded(int index, TaskList taskList, int count) {
        return (index == taskList.getSize() - 1 && count == 1);
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
