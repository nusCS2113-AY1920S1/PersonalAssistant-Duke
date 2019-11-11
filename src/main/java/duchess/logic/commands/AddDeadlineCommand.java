package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Grade;
import duchess.model.Module;
import duchess.model.task.Deadline;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Command to add a given deadline task to the tasklist.
 */
public class AddDeadlineCommand extends Command {
    private static final String INVALID_MODULE_MSG = "Unable to find given module.";
    private String description;
    private LocalDateTime deadline;
    private String moduleCode;
    private int weightage;

    /**
     * Create a command to add a deadline.
     *
     * @param description String containing description of deadline task
     * @param deadline    LocalDateTime object of deadline task
     */
    public AddDeadlineCommand(String description, LocalDateTime deadline) {
        this.description = description;
        this.deadline = deadline;
        this.moduleCode = null;
    }

    public AddDeadlineCommand(String description, LocalDateTime deadline, String moduleCode) {
        this(description, deadline);
        this.moduleCode = moduleCode;
    }

    public AddDeadlineCommand(String description, LocalDateTime deadline, String moduleCode, int weightage) {
        this(description, deadline, moduleCode);
        this.weightage = weightage;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Task task = new Deadline(description, deadline);
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
