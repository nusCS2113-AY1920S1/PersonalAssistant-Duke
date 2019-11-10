package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;

/**
 * Allows the user to restore to a particular version.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class RestoreVersionCommand extends Command {
    private int versionNumber;

    public RestoreVersionCommand(int versionNumberToStore) {
        this.versionNumber = versionNumberToStore;
    }

    /**
     * Allows the user to restore from pre-stored versions.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @param history Allows the history features to be done.
     */
    @Override
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        tasks.updateListOfTasks(history.restoreVersion(tasks.getTasks(), versionNumber));
        tasks.updateGui(null);
        storage.saveFile(tasks.getTasks());
    }
}
