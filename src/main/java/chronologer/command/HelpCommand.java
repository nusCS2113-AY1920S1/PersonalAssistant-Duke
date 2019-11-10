package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

/**
 * Provides the user with a list of commands they can utilise.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class HelpCommand extends Command {
    /**
     * Passes the user help manual to the GUI.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        UiMessageHandler.outputMessage(UiMessageHandler.printManual());
    }
}