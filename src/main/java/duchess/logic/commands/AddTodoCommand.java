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

public class AddTodoCommand extends Command {
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
        Task task;
        Grade grade;
        if (moduleCode != null) {
            task = new Todo(description);
            Optional<Module> module = store.findModuleByCode(moduleCode);
            task.setModule(module.orElseThrow(() ->
                    new DuchessException("Unable to find given module.")
            ));
            grade = new Grade(description, weightage);
            task.setGrade(grade);
            module.get().addGrade(grade);
        } else {
            task = new Todo(description);
        }
        store.getTaskList().add(task);
        ui.showTaskAdded(store.getTaskList(), task);
        storage.save(store);
    }
}
