package commands;

import members.Member;
import core.Ui;
import tasks.Event;
import tasks.Task;
import utils.CommandResult;
import utils.DukeException;
import utils.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * This is a class for handling recurring events command.
 *
 *  @author Justin Chia
 */
public class RecurringCommand extends Command {
    protected int index;
    protected int numWeeks;

    /**
     * @param line is the task in which to convert to recurring, while numWeeks describe num of weeks to recur
     * @author Justin Chia
     */
    public RecurringCommand(String line) {
        String[] splites = line.trim().split("\\s+", 2);
        this.index = Integer.parseInt(splites[0]);
        this.numWeeks = Integer.parseInt(splites[1]) - 1;
    }


    @Override
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage)
            throws DukeException {
        try {
            Event temp = (Event) tasks.get(index - 1); //TODO Generalise implementation of recurrence
            Date originalDate = temp.getAt();
            String originalDescription = temp.getDescription();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(originalDate);
            for (int i = 1; i <= numWeeks; i += 1) {
                calendar.add(Calendar.DATE, 7);
                Event newEvent = new Event(originalDescription, calendar.getTime());
                newEvent.setRecurring(numWeeks);
                tasks.add(newEvent);
            }
            tasks.get(index - 1).setRecurring(numWeeks);
            storage.storeTaskList(tasks);
            return new CommandResult("The task "
                    + tasks.get(index - 1).getDescription()
                    +
                    " has been converted to recur for " + numWeeks + " weeks");
        } catch (Exception e) {
            throw new DukeException("Not a valid recurrence command, recurrence can only be used on events");
        }
    }
}
