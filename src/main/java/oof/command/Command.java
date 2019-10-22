package oof.command;

import oof.exception.OofException;
import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.Todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Comparator;
import java.util.Date;

/**
 * Represents a Command object that corresponds to specific commands
 * defined by the user.
 * Abstract parent of all other Command subclasses.
 */
public abstract class Command {

    /**
     * Invokes other Command subclasses based on the input given by the user.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    public abstract void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException;

    /**
     * Parses the Timestamp given by the user and returns the parsed
     * date as a string if the date is valid.
     *
     * @param date Timestamp supplied by user.
     * @return Parsed Timestamp if the Timestamp is valid, else returns "failed".
     */
    public String parseTimeStamp(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date parsed = format.parse(date);
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
    protected boolean isDateValid(String date) {
        return !date.equals("failed");
    }

    /**
     * Converts a date into a string.
     *
     * @param date The date to be converted.
     * @return A string in the date format specified.
     */
    public String convertDateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    /**
     * Converts a string into a date.
     *
     * @param date The string to be converted.
     * @return A date in the date format specified.
     * @throws ParseException Throws an exception if datetime cannot be parsed.
     */
    public Date convertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("HH:mm").parse(date);
    }

    /**
     * Get Date from Task object.
     *
     * @param task Task object.
     * @return String containing date from Task object.
     */
    protected String getDate(Task task) {
        if (task instanceof Todo) {
            return ((Todo) task).getOn().substring(0, 10);
        } else if (task instanceof Deadline) {
            return ((Deadline) task).getBy().substring(0, 10);
        } else if (task instanceof Event) {
            return ((Event) task).getStartTime().substring(0, 10);
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
     * @return true if ExitCommand is called, false otherwise.
     */
    public abstract boolean isExit();
}