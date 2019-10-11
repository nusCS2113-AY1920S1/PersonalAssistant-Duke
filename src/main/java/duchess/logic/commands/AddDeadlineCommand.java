package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.model.task.Deadline;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;
import java.util.Optional;

public class AddDeadlineCommand extends Command {
    /** List containing String objects. */
    private List<String> words;

    public AddDeadlineCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Task task;
        if (words.get(words.size() - 1).charAt(0) == '#') {
            task = new Deadline(words.subList(0, words.size() - 1));
            Optional<Module> module = store.findModuleByCode(words.get(words.size() - 1).substring(1));
            task.setModule(module.orElseThrow(() ->
                    new DuchessException("Unable to find given module.")
            ));
        } else {
            task = new Deadline(words.subList(0, words.size()));
        }
        store.getTaskList().add(task);
        ui.showTaskAdded(store.getTaskList(), task);
        storage.save(store);
    }
}
