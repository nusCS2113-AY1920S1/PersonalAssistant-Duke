package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Grade;
import duchess.model.Module;
import duchess.model.calendar.CalendarEntry;
import duchess.model.calendar.CalendarManager;
import duchess.model.task.Event;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Command to add a given event task to the tasklist.
 */
public class AddEventCommand extends Command {
    private String description;
    private LocalDateTime end;
    private LocalDateTime start;
    private String moduleCode;
    private int weightage;

    /**
     * Creates a command to add an event.
     *
     * @param description description of Event task
     * @param end         end time
     * @param start       start time
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

    public AddEventCommand(
            String description, LocalDateTime end, LocalDateTime start, String moduleCode, int weightage) {
        this(description, end, start, moduleCode);
        this.weightage = weightage;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Event task = new Event(description, end, start);
        Grade grade;
        if (moduleCode != null) {
            Optional<Module> module = store.findModuleByCode(moduleCode);
            task.setModule(module.orElseThrow(() ->
                    new DuchessException("Unable to find given module.")
            ));
            grade = new Grade(description, weightage);
            task.setGrade(grade);
            module.get().addGrade(grade);
        }
        if (store.isClashing(task)) {
            throw new DuchessException("Unable to add event - clash found.");
        }
        store.getTaskList().add(task);
        if (task.isCalendarEntry()) {
            List<CalendarEntry> ce = store.getDuchessCalendar();
            CalendarManager.addEntry(ce, task, start.toLocalDate());
            store.setDuchessCalendar(ce);
        }
        ui.showTaskAdded(store.getTaskList(), task);
        storage.save(store);
    }
}