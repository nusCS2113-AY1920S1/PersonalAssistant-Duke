package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeleteModuleCommand extends Command {
    private final int moduleNo;

    public DeleteModuleCommand(int moduleNo) {
        this.moduleNo = moduleNo - 1;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        try {
            Module toRemove = store.getModuleList().get(moduleNo);
            List<Task> tasks = store.getTaskList();
            List<Task> associatedTasks = tasks.stream()
                    .filter(task -> task.getModule().equals(Optional.of(toRemove)))
                    .collect(Collectors.toList());

            if (associatedTasks.size() == 0) {
                store.getModuleList().remove(moduleNo);
                ui.showDeletedModule(toRemove);
                storage.save(store);
            } else {
                ui.showUnableToDeleteModuleMsg(associatedTasks);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException("Please supply a valid number.");
        }
    }
}
