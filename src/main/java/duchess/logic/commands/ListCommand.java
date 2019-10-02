package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.TaskList;
import duchess.storage.Store;
import duchess.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DukeException {
        ui.showTaskList(store.getTaskList());
    }
}
