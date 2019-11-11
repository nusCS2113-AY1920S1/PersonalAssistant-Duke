package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.log.Log;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to add a given module to the store.
 */
public class AddModuleCommand extends Command {
    private static final String DUPLICATE_ERROR_MSG = "Another module with the same code exists.";

    private final String moduleCode;
    private final String moduleName;
    private final Logger logger = Log.getLogger();

    /**
     * Create a command to add a module.
     *
     * @param moduleName the name of the module
     * @param moduleCode the code of the module
     */
    public AddModuleCommand(String moduleName, String moduleCode) {
        assert moduleName != null;
        assert moduleCode != null;

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
        logger.log(Level.INFO, "Adding module: " + module);

        ensureNoDuplicateModule(module, store);
        store.getModuleList().add(module);
        ui.showModuleAdded(module, store.getModuleList());
        storage.save(store);
    }

    private void ensureNoDuplicateModule(Module module, Store store) throws DuchessException {
        boolean hasDuplicate = store.getModuleList().stream()
                .anyMatch(existingModule -> existingModule.equals(module));
        if (hasDuplicate) {
            logger.log(Level.INFO, DUPLICATE_ERROR_MSG);
            throw new DuchessException(DUPLICATE_ERROR_MSG);
        }
    }
}
