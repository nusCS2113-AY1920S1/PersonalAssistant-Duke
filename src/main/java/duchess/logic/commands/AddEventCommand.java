package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.model.task.Event;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.time.LocalDateTime;
import java.util.Optional;

public class AddEventCommand extends Command {
    private String description;
    private LocalDateTime end;
    private LocalDateTime start;
    private String moduleCode;

    /**
     * Creates a command to add an event.
     *
     * @param description description of Event task
     * @param end end time
     * @param start start time
     */
    public AddEventCommand(String description, LocalDateTime end, LocalDateTime start) {
        this.description = description;
        this.end = end;
        this.start = start;
        this.moduleCode = null;
    }

    public AddEventCommand(String description, LocalDateTime end, LocalDateTime start, String moduleCode) {
        this(description, end, start);
        this.moduleCode = moduleCode;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Event task = new Event(description, end, start);
        if (moduleCode != null) {
            Optional<Module> module = store.findModuleByCode(moduleCode);
            task.setModule(module.orElseThrow(() ->
                    new DuchessException("Unable to find given module.")
            ));
        }
        if (store.isClashing(task)) {
            throw new DuchessException("Unable to add event - clash found.");
        }
        store.getTaskList().add(task);
        ui.showTaskAdded(store.getTaskList(), task);
        storage.save(store);
    }
}