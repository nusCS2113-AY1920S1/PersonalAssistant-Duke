package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Grade;
import duchess.model.Module;
import duchess.model.task.Task;
import duchess.model.task.Todo;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Optional;

/**
 * Command to add a given todo task to the tasklist.
 */
public class AddTodoCommand extends Command {
    private static final String INVALID_MODULE_MSG = "Unable to find given module.";
    private String description;
    private String moduleCode;
    private int weightage;


    public AddTodoCommand(String description) {
        this.description = description;
        this.moduleCode = null;
    }

    public AddTodoCommand(String description, String moduleCode) {
        this.description = description;
        this.moduleCode = moduleCode;
    }

    /**
     * Creates a command to add a Todo task.
     *
     * @param description description of Todo task
     * @param moduleCode Code of the associated module
     * @param weightage weightage of the associated grade
     */
    public AddTodoCommand(String description, String moduleCode, int weightage) {
        this.description = description;
        this.moduleCode = moduleCode;
        this.weightage = weightage;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Task task = new Todo(description);
        Grade grade;
        if (moduleCode != null) {
            Optional<Module> module = store.findModuleByCode(moduleCode);
            task.setModule(module.orElseThrow(() ->
                    new DuchessException(INVALID_MODULE_MSG)
            ));
            grade = new Grade(description, weightage);
            task.setGrade(grade);
            module.get().addGrade(grade);
        }
        store.getTaskList().add(task);
        ui.showTaskAdded(store.getTaskList(), task);
        storage.save(store);
    }
}
