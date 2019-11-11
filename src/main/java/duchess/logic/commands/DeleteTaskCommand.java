package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.log.Log;
import duchess.model.calendar.CalendarEntry;
import duchess.model.calendar.CalendarManager;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to remove given task from the tasklist.
 */
public class DeleteTaskCommand extends Command {
    private static final String SUPPLY_VALID_NUMBER_MSG = "Please supply a valid number.";
    private final int taskNo;
    private final Logger logger = Log.getLogger();

    public DeleteTaskCommand(int taskNo) {
        this.taskNo = taskNo - 1;
    }

    /**
     * Deletes a user specified task.
     *
     * @param store the store
     * @param ui Userinterface object
     * @param storage Storage object
     * @throws DuchessException Exception thrown when errors besides invalid format and index are found
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        try {
            Task toRemove = store.getTaskList().get(taskNo);
            store.getTaskList().remove(taskNo);
            ui.showDeletedTask(store.getTaskList(), toRemove);
            if (toRemove.isCalendarEntry()) {
                List<CalendarEntry> update = store.getDuchessCalendar();
                LocalDate date = toRemove.getTimeFrame().getStart().toLocalDate();
                update = CalendarManager.deleteEntry(update, toRemove, date);
                store.setDuchessCalendar(update);
                logger.log(Level.INFO, "Deleted event from calendar: " + toRemove.toString());
            }
            logger.log(Level.INFO, "Deleted event: " + toRemove.toString());
            storage.save(store);
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException(SUPPLY_VALID_NUMBER_MSG);
        }
    }
}
