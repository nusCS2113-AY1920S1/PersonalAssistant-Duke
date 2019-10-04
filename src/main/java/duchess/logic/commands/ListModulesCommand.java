package duchess.logic.commands;

import duchess.exceptions.DukeException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

public class ListModulesCommand extends Command {
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DukeException {
        ui.showModuleList(store.getModuleList());
    }
}
