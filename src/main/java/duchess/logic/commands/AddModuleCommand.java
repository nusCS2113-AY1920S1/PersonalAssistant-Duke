package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

public class AddModuleCommand extends Command {
    private String moduleCode;
    private String moduleName;

    /**
     * Create a command to add a module.
     *
     * @param moduleName the name of the module
     * @param moduleCode the code of the module
     */
    public AddModuleCommand(String moduleName, String moduleCode) {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Module module = new Module(moduleCode, moduleName);
        store.getModuleList().add(module);
        ui.showModuleAdded(module, store.getModuleList());
        storage.save(store);
    }
}
