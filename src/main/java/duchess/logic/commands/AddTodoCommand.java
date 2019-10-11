package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.model.task.Task;
import duchess.model.task.Todo;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;
import java.util.Optional;

public class AddTodoCommand extends Command {
    private List<String> words;

    public AddTodoCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Task task;
        if (words.get(words.size() - 1).charAt(0) == '#') {
            task = new Todo(words.subList(0, words.size() - 1));
            Optional<Module> module = store.findModuleByCode(words.get(words.size() - 1).substring(1));
            task.setModule(module.orElseThrow(() ->
                    new DuchessException("Unable to find given module.")
            ));
        } else {
            task = new Todo(words.subList(0, words.size()));
        }
        store.getTaskList().add(task);
        ui.showTaskAdded(store.getTaskList(), task);
        storage.save(store);
    }
}
