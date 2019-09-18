package commands;

import tasks.Event;
import tasks.Task;
import utils.DukeException;
import utils.Storage;
import core.Ui;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Justin Chia
 * This class handles the change to recurring command
 */
public class RecurringCommand extends Command {
    protected int index;
    protected int numWeeks;

    /**
     * @author Justin Chia
     * @param index is the task in which to convert to recurring, while numWeeks describe num of weeks to recur
     */
    public RecurringCommand(String index, String numWeeks){
    this.index = Integer.parseInt(index);
    this.numWeeks = Integer.parseInt(numWeeks) - 1;
    }


    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) throws DukeException {
        try {
            Event temp = (Event)tasks.get(index-1); //TODO Generalise implementation of recurrence
            Date originalDate = temp.getAt();
            String originalDescription = temp.getDescription();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(originalDate);
            for(int i=1; i<=numWeeks; i+=1){
                calendar.add(Calendar.DATE, 7);
                Event newEvent = new Event(originalDescription, calendar.getTime());
                newEvent.setRecurring(numWeeks);
                tasks.add(newEvent);
            }

            tasks.get(index - 1).setRecurring(numWeeks);
            storage.store(tasks);
            Ui.print("The task " + tasks.get(index-1).getDescription() + " has been converted to recur for " + numWeeks + " weeks");
        } catch (Exception e) {
            throw new DukeException("Not a valid recurrence command");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
