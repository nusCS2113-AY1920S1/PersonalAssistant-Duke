package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.model.task.Task;
import duchess.model.task.Todo;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Optional;

public class AddTodoCommand extends Command {
    private String description;
    private String moduleCode;

    public AddTodoCommand(String description) {
        this.description = description;
        this.moduleCode = null;
    }

    public AddTodoCommand(String description, String moduleCode) {
        this.description = description;
        this.moduleCode = moduleCode;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Task task;
        if (moduleCode != null) {
            task = new Todo(description);
            Optional<Module> module = store.findModuleByCode(moduleCode);
            task.setModule(module.orElseThrow(() ->
                    new DuchessException("Unable to find given module.")
            ));
        } else {
            task = new Todo(description);
        }
        store.getTaskList().add(task);
        ui.showTaskAdded(store.getTaskList(), task);
        storage.save(store);
    }
}
