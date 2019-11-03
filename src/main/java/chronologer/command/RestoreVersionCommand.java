package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;

public class RestoreVersionCommand extends Command {
    private int versionNumber;

    public RestoreVersionCommand(int versionNumberToStore) {
        this.versionNumber = versionNumberToStore;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        tasks.updateListOfTasks(ChronologerStateList.restoreVersion(tasks.getTasks(), versionNumber));
        tasks.updatePriority(null);
        storage.saveFile(tasks.getTasks());
    }
}
