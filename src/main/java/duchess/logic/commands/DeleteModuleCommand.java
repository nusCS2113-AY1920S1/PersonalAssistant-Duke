package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeleteModuleCommand extends Command {
    private List<String> words;

    public DeleteModuleCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        try {
            int moduleNo = Integer.parseInt(words.get(1)) - 1;
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
        } catch (NumberFormatException e) {
            throw new DuchessException("Please supply a number. Eg: done 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException("Please supply a valid number.");
        }
    }
}
