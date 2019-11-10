package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.calendar.CalendarManager;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

/**
 * Command to remove given task from the tasklist.
 */
public class DeleteTaskCommand extends Command {
    private final int taskNo;

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
            store.setDuchessCalendar(CalendarManager.deleteEntry(store.getDuchessCalendar(), toRemove));
            storage.save(store);
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException("Please supply a valid number.");
        }
    }
}
