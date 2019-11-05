package oof.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Comparator;
import java.util.Date;

import oof.Ui;
import oof.exception.CommandException.CommandException;
import oof.model.module.SemesterList;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.task.Todo;
import oof.storage.StorageManager;

/**
 * Represents a Command object that corresponds to specific commands
 * defined by the user.
 * Abstract parent of all other Command subclasses.
 */
public abstract class Command {

    private static final int DESCRIPTION_LENGTH_MAX = 20;

    /**
     * Invokes other Command subclasses based on the input given by the user.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws CommandException if command fails to execute.
     */
    public abstract void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws CommandException;

    /**
     * Checks if description and module code exceeds the maximum description length.
     *
     * @return True if maximum description length is exceeded, false otherwise.
     */
    boolean exceedsMaxLength(String description) {
        return description.length() >= DESCRIPTION_LENGTH_MAX;
    }

    /**
     * Parses the Timestamp given by the user and returns the parsed
     * date as a string if the date is valid.
     *
     * @param dateTime Timestamp supplied by user.
     * @return Parsed Timestamp if the Timestamp is valid, else returns "failed".
     */
    String parseDateTime(String dateTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date parsed = format.parse(dateTime);
            return format.format(parsed);
        } catch (ParseException | DateTimeException e) {
            return "failed";
        }
    }

    /**
     * Parses the Date input by user and returns the parsed date if the date is valid.
     *
     * @param date Date input by user
     * @return Parsed Date if the Date is valid, else returns "failed".
     */
    String parseDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date parsed = format.parse(date);
            return format.format(parsed);
        } catch (ParseException | DateTimeException e) {
            return "failed";
        }
    }

    /**
     * Parses the Time given by the user and returns the parsed time as a string if the date is valid.
     *
     * @param time String containing time supplied by user.
     * @return Parsed Time if the Time is valid, else return "failed".
     */
    public String parseTime(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date parsed = format.parse(time);
            return format.format(parsed);
        } catch (ParseException | DateTimeException e) {
            return "failed";
        }
    }

    /**
     * Checks if date is valid.
     *
     * @param date String processed by ParseTimeStamp method.
     * @return true if date is not equals to "failed", false otherwise.
     */
    boolean isDateValid(String date) {
        return !date.equals("failed");
    }

    /**
     **
     * Converts a string into a time.
     *
     * @param time The string to be converted.
     * @return A time in the format specified.
     * @throws ParseException Throws an exception if time cannot be parsed.
     */
    public Date convertStringToTime(String time) throws ParseException {
        return new SimpleDateFormat("HH:mm").parse(time);
    }

    /**
     * Converts a string into a date.
     *
     * @param date The string to be converted.
     * @return A date in the format specified.
     * @throws ParseException Throws an exception if date cannot be parsed.
     */
    public Date convertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    /**
     * Convert a Date Object into a string.
     *
     * @param date The date to be converted.
     * @return A string in the format specified.
     */
    public String convertDatetoString(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    /**
     * Get Date from Task object.
     *
     * @param task Task object.
     * @return String containing date from Task object.
     */
    String getDate(Task task) {
        if (task instanceof Todo) {
            return ((Todo) task).getTodoDate().substring(0, 10);
        } else if (task instanceof Deadline) {
            return ((Deadline) task).getDeadlineDateTime().substring(0, 10);
        } else if (task instanceof Event) {
            return ((Event) task).getStartDateTime().substring(0, 10);
        }
        return null;
    }

    /**
     * Comparator to sort tasks by their time in ascending order.
     */
    class SortByTime implements Comparator<String[]> {
        @Override
        public int compare(String[] a, String[] b) {
            if (a[0].equals("")) {
                return -1;
            } else if (b[0].equals("")) {
                return 1;
            }
            int hour1 = Integer.parseInt(a[0].substring(0, 2));
            int hour2 = Integer.parseInt(b[0].substring(0, 2));
            if (hour1 != hour2) {
                return hour1 - hour2;
            } else {
                int minute1 = Integer.parseInt(a[0].substring(3, 5));
                int minute2 = Integer.parseInt(b[0].substring(3, 5));
                return minute1 - minute2;
            }
        }

        @Override
        public boolean equals(Object object) {
            return this == object;
        }
    }


    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }

}