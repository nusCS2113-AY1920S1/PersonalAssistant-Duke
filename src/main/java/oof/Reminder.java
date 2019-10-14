package oof;


import oof.task.Deadline;
import oof.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

/**
 * Represents a Reminder class to remind user on upcoming deadlines.
 */
public class Reminder {

    private static final int INDEX_DATE = 1;
    private static final long UPCOMING_THRESHOLD = 24;
    private static final long MILLISECOND_TO_HOUR = 60 * 60 * 1000;

    /**
     * Checks Task objects dates to determine if it is due soon.
     *
     * @param taskList TaskList that contains Task objects.
     * @param ui       Ui that is responsible for visual feedback.
     */
    public void checkDeadline(TaskList taskList, Ui ui) {
        ui.printReminder();
        int count = 1;
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            if (task instanceof Deadline) {
                String[] lineSplit = task.toString().split("by: ");
                String result = lineSplit[INDEX_DATE].substring(0, lineSplit[INDEX_DATE].length() - 1);
                result = result.trim();
                try {
                    Date dueDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(result);
                    Date now = new Date();
                    if (isUpcoming(dueDate, now)) {
                        ui.printUpcomingDeadline(count, taskList.getTask(i));
                        count++;
                    }
                } catch (ParseException | DateTimeException e) {
                    System.out.println("Timestamp given is invalid! Please try again.");
                }

            }
        }
        ui.printLine();
    }

    /**
     * Checks if deadline is within the next 24 hours.
     *
     * @param dueDate Due date of current deadline.
     * @param now     Current time.
     * @return true if due date of current deadline is within 24 hours of current time.
     */
    private boolean isUpcoming(Date dueDate, Date now) {
        long diff = dueDate.getTime() - now.getTime(); // difference in time in milliseconds
        long diffHours = diff / MILLISECOND_TO_HOUR;
        return diffHours < UPCOMING_THRESHOLD && diffHours > 0;
    }
}
