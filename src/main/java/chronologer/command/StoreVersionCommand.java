package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;

public class StoreVersionCommand extends Command {
    private int versionNumber;

    public StoreVersionCommand(int versionNumberToStore) {
        this.versionNumber = versionNumberToStore;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        ChronologerStateList.storeVersion(tasks.getTasks(), versionNumber);
        tasks.updateGUI(null);
        storage.saveFile(tasks.getTasks());
    }
}
