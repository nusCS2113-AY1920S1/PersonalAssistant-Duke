package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.calendar.CalendarEntry;
import duchess.model.calendar.CalendarManager;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.time.LocalDate;
import java.util.List;

/**
 * Command to mark specified task as done.
 */
public class DoneTaskCommand extends Command {
    private int taskNo;

    public DoneTaskCommand(int taskNo) {
        this.taskNo = taskNo - 1;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        try {
            Task task = store.getTaskList().get(taskNo);
            task.setDone(true);
            if (task.isCalendarEntry()) {
                List<CalendarEntry> update = store.getDuchessCalendar();
                LocalDate date = task.getTimeFrame().getStart().toLocalDate();
                update = CalendarManager.deleteEntry(update, task, date);
                CalendarManager.addEntry(update, task, date);
                store.setDuchessCalendar(update);
            }
            ui.showDoneTask(task);
            storage.save(store);
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException("Please supply a valid number.");
        }
    }
}
