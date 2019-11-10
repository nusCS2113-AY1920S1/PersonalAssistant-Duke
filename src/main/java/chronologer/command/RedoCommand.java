package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

/**
 * Allows the user to delete a particular task from their task list based on index.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class RedoCommand extends Command {
    /**
     * Performs the redo for the user by updating the core tasklist.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        tasks.updateListOfTasks(ChronologerStateList.redo());
        tasks.updateGui(null);
        storage.saveFile(tasks.getTasks());
        UiMessageHandler.outputMessage("Redo successful!");
    }
}