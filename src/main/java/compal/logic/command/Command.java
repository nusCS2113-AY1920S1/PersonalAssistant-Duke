package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public abstract class Command {
    /**
     * CONSTANTS.
     */
    private static final int DEFAULT_WEEK_INTERVAL = 7;

    /**
     * Executes the command and returns the result message.
     *
     * @param task {@code Model} which the command should operate on.
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult commandExecute(TaskList task) throws CommandException;


    /**
     * Increases date by week, to assign event slots for each week.
     *
     * @param initialDate The date to increment
     * @return Final date one week later than initialDate
     * @author Yue Jun Yi, yueyeah
     */
    public Date incrementDateByWeek(Date initialDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.add(Calendar.DATE, DEFAULT_WEEK_INTERVAL);
        Date finalDate = calendar.getTime();
        return finalDate;
    }


    /**
     * Converts a date string to a Date object.
     *
     * @param dateStr The date string to be converted.
     * @return The date string in the form of a Date object.
     * @author Yue Jun Yi, yueyeah
     */
    public Date stringToDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Converts a Date object to a date string. Correct type for creating a Task object.
     *
     * @param date The date in the form of a Date object.
     * @return The date in the form of a String object.
     * @author Yue Jun Yi, yueyeah
     */
    public String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }
}