package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

public class ListModulesCommand extends Command {
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        ui.showModuleList(store.getModuleList());
    }
}
