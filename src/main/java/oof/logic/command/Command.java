package oof.logic.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Comparator;
import java.util.Date;

import oof.commons.exceptions.command.CommandException;
import oof.model.task.TaskList;
import oof.model.university.SemesterList;
import oof.storage.StorageManager;
import oof.ui.Ui;

/**
 * Represents a Command object that corresponds to specific commands
 * defined by the user.
 * Abstract parent of all other Command subclasses.
 */
public abstract class Command {

    private static final int INDEX_TIME = 0;
    private static final int INDEX_DESCRIPTION = 1;
    private static final int INDEX_HOUR_START = 0;
    private static final int INDEX_HOUR_END = 2;
    private static final int INDEX_MINUTE_START = 3;
    private static final int INDEX_MINUTE_END = 5;


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
    public boolean exceedsMaxLength(String description, int limit) {
        return description.length() >= limit;
    }

    /**
     * Parses the Timestamp given by the user and returns the parsed
     * date as a string if the date is valid.
     *
     * @param dateTime Timestamp supplied by user.
     * @return Parsed Timestamp if the Timestamp is valid, else returns "failed".
     */
    public String parseDateTime(String dateTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            format.setLenient(false);
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
    public String parseDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            format.setLenient(false);
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
            format.setLenient(false);
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
    public boolean isDateValid(String date) {
        return !date.equals("failed");
    }

    /**
     * *
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
    public String convertDateToString(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    /**
     * Comparator to sort tasks by their time in ascending order.
     */
    public class SortByTime implements Comparator<String[]> {
        @Override
        public int compare(String[] a, String[] b) {
            if (a[INDEX_TIME].equals("")) {
                return -1;
            } else if (b[INDEX_TIME].equals("")) {
                return 1;
            }
            int hour1 = Integer.parseInt(a[INDEX_TIME].substring(INDEX_HOUR_START, INDEX_HOUR_END));
            int hour2 = Integer.parseInt(b[INDEX_TIME].substring(INDEX_HOUR_START, INDEX_HOUR_END));
            int minute1 = Integer.parseInt(a[INDEX_TIME].substring(INDEX_MINUTE_START, INDEX_MINUTE_END));
            int minute2 = Integer.parseInt(b[INDEX_TIME].substring(INDEX_MINUTE_START, INDEX_MINUTE_END));
            if (hour1 != hour2) {
                return hour1 - hour2;
            } else if (minute1 != minute2) {
                return minute1 - minute2;
            } else {
                return a[INDEX_DESCRIPTION].compareTo(b[INDEX_DESCRIPTION]);
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