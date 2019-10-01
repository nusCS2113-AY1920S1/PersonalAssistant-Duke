package Commands;

import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;
import javafx.util.Pair;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the command to find earliest free time from TaskList object
 */
public class FindEarliestFreeTimesCommand extends Command {
    private String duration;

    /**
     * Creates a FindEarliestFreeTimesCommand object.
     * @param duration The duration that the user wants to use to find in the TaskList object
     */
    public FindEarliestFreeTimesCommand(String duration) {
        this.duration = duration;
    }

    /**
     * Executes the finding of earliest available block period inside the TaskList object with the given duration.
     * @param ui The Ui object to display the earliest free time message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display find free time message
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");
        Pair<Date, Date> freeTime = events.findEarliestFreeTime(duration);
        String message = dateFormat.format(freeTime.getKey()) + " until " + dateFormat.format(freeTime.getValue());
        return ui.showFreeTimes(message);
    }
}
