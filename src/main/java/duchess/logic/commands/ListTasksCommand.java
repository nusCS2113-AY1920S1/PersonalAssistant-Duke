package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

public class ListTasksCommand extends Command {
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        storage.setPreviousUndoFalse();
        ui.showTaskList(store.getTaskList());
    }
}
