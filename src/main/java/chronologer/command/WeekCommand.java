package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

/**
 * Allows the viewing of different weeks.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class WeekCommand extends Command {
    private int weekNumber;

    public WeekCommand(int weekToStore) {
        this.weekNumber = weekToStore;
    }

    /**
     * Allows the user to view the tasks of a desired week.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @param history Allows the history features to be done.
     */
    @Override
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        UiMessageHandler.outputMessage(tasks.updateWeek(weekNumber));
        tasks.updateGui(null);
    }
}
