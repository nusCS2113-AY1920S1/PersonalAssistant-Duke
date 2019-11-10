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

    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        tasks.updateListOfTasks(ChronologerStateList.restoreVersion(tasks.getTasks(), versionNumber));
        tasks.updateGui(null);
        storage.saveFile(tasks.getTasks());
    }
}
