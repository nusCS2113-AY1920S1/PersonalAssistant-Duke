package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.log.Log;
import duchess.model.Module;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Command to delete given module.
 */
public class DeleteModuleCommand extends Command {
    private static final String VALID_NUMBER_MSG = "Please supply a valid number.";

    private final int moduleNo;
    private final Logger logger = Log.getLogger();

    /**
     * Deletes the module given an index.
     *
     * @param moduleNo the index of the module to delete
     */
    public DeleteModuleCommand(int moduleNo) {
        this.moduleNo = moduleNo - 1;
    }

    /**
     * Deletes a module if there are no associated tasks.
     *
     * @param store the application store
     * @param ui the application ui
     * @param storage the storage instance
     * @throws DuchessException if the index is invalid
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        try {
            Module toRemove = store.getModuleList().get(moduleNo);
            List<Task> tasks = store.getTaskList();
            List<Task> associatedTasks = tasks.stream()
                    .filter(task -> task.getModule().equals(Optional.of(toRemove)))
                    .collect(Collectors.toList());

            if (associatedTasks.size() == 0) {
                logger.log(Level.INFO, "Deleting module #" + moduleNo);
                store.getModuleList().remove(moduleNo);
                ui.showDeletedModule(toRemove);
                storage.save(store);
            } else {
                logger.log(Level.INFO, "Not deleting module #" + moduleNo);
                ui.showUnableToDeleteModuleMsg(associatedTasks);
            }
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Attempted to delete non-existent module #" + moduleNo);
            throw new DuchessException(VALID_NUMBER_MSG);
        }
    }
}
