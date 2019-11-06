package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

/**
 * Command to add a given module to the store.
 */
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

    /**
     * Instantiates the module, adds it to the store and saves it.
     * @param store the application store
     * @param ui the Ui object to give feedback to the user
     * @param storage the storage object to save the store to the disk
     * @throws DuchessException if there is an error creating the module or saving the module
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Module module = new Module(moduleCode, moduleName);
        store.getModuleList().add(module);
        ui.showModuleAdded(module, store.getModuleList());
        storage.save(store);
    }
}
