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
                String[] lineSplit = task.toString().split("by:");
                String result = lineSplit[1].substring(0, lineSplit[1].length() - 1); // remove closing bracket
                result = result.trim();
                try {
                    Date dueDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(result);
                    Date now = new Date(); // current date time
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
        long diffHours = diff / (60 * 60 * 1000);
        return diffHours <= 24;
    }
}
