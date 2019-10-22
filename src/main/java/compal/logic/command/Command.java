package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;
import java.util.Calendar;
import java.util.Date;


public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param task {@code Model} which the command should operate on.
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult commandExecute(TaskList task) throws CommandException;


    //@@author yueyeah
    /**
     * Increases date by week, to assign event slots for each week.
     *
     * @param initialDate The date to increment
     * @return Final date one week later than initialDate
     */
    public Date incrementDateByDays(Date initialDate, int numOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.add(Calendar.DATE, numOfDays);
        Date finalDate = calendar.getTime();
        return finalDate;
    }
}