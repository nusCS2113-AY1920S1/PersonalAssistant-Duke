package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

/**
 * Allows the user to undo a change to the core tasklist.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class UndoCommand extends Command {

    /**
     * Performs the undo for the user by updating the core tasklist.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @param history Allows the history features to be done.
     */
    @Override
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        tasks.updateListOfTasks(history.undo());
        tasks.updateGui(null);
        storage.saveFile(tasks.getTasks());
        UiMessageHandler.outputMessage("Undo successful!");
    }
}