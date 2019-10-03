package seedu.duke.data;

import seedu.duke.task.Task;
import seedu.duke.ui.Ui;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

/**
 * This class stores the Scheduling of Tasks such that it will be easier for Duke to retrieve Tasks based
 * off the Date given.
 */
public class Schedule {
    /**
     * This formatter allows only the storage of the Date. Excludes Time.
     */
    private static final String DATE_FORMATTER_NO_TIME = "dd/MM/yyyy";
    /**
     * Tree map stores the Dates in ascending order which allows easier finding of Task.
     */
    private static TreeMap<Date, ArrayList<Task>> schedulesInOrder = new TreeMap<Date, ArrayList<Task>>();
    private Ui ui;


    public Schedule() {
        ui = new Ui();
    }

    /**
     * Adds the Task into the TreeMap with proper Date storage.
     * @param task the task given
     * @param date the date of the task given (excludes time).
     * @return the ArrayList that contains the task.
     * @throws ParseException if user inputs the wrong format of Date.
     */
    public ArrayList<Task> addToSchedule(Task task, Date date) throws ParseException {
        if (!schedulesInOrder.containsKey(date)) {
            schedulesInOrder.put(date, new ArrayList<>());
        }
        schedulesInOrder.get(date).add(task);
        return schedulesInOrder.get(date);
    }

    /**
     * Prints all Tasks in the given Date.
     * @param date the date (excludes Time).
     */
    public void printSchedule(Date date) {
        System.out.println("\t_____________________________________");
        String strDate = convertDateToString(date);
        if (schedulesInOrder.containsKey(date)) {
            System.out.println("\tHere are the tasks on this date: (" + strDate + ")");
            int i = 1;
            for (Task task : schedulesInOrder.get(date)) {
                System.out.println("\t" + i + ". " + task.toString());
                i++;
            }
        } else {
            System.out.println("\tThere are no tasks in the given date: (" + strDate + ")");
        }
        System.out.println("\t_____________________________________");
    }

    /**
     * Converts String to Date. Catches exception if String cannot be convert to the
     * date in the given format.
     * @param dateStr the String of the date to be parsed.
     * @return the date if String is valid, and null if String is not valid.
     */
    public Date convertStringToDate(String dateStr) {
        try {
            Date date = new SimpleDateFormat(DATE_FORMATTER_NO_TIME).parse(dateStr);
            return date;
        } catch (ParseException e) {
            ui.dateFormatError();
            return null;
        }
    }

    /**
     * Converts the Date to String.
     * @param date the Date in DD/MM/uuuu format.
     * @return the String in "DD/MM/uuu".
     */
    public String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER_NO_TIME);
        return dateFormat.format(date);
    }

    /**
     * Checks if the String can be convert to a Date format.
     * @param dateStr String of the date.
     * @return true if String can be parsed as a valid Date type.
     */
    public static boolean isValidDate(String dateStr) {
        try {
            Date date = new SimpleDateFormat(DATE_FORMATTER_NO_TIME).parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Gets the ArrayList from the MapTree with the key of Date.
     * @param date the Date value that is the key of the TreeMap.
     * @return the ArrayList of the key date.
     */
    public ArrayList<Task> getDatedList(Date date) {
        return schedulesInOrder.get(date);
    }

}
