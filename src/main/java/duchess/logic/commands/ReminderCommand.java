package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Searches Tasklist and filters out deadline objects.
 */
public class ReminderCommand extends Command {

    /**
     * Displays Deadline objects to user in ascending order.
     *
     * @param store List containing tasks
     * @param ui Userinterface object
     * @param storage Storage object
     * @throws DuchessException Exception thrown when storage not found
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        List<Task> reminderList = addTimedActivities(store.getTaskList());
        Collections.sort(reminderList);
        display(reminderList, ui);

        storage.setPreviousUndoFalse();
    }

    /**
     * Returns a List of Task objects.
     * Adds objects of type Deadline and Event to reminderList.
     *
     * @param tasks list of tasks
     */
    private List<Task> addTimedActivities(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getReminder)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    /**
     * Displays deadlines to user.
     */
    private void display(List<Task> reminderList, Ui ui) {
        if (reminderList.size() == 0) {
            ui.showNoDeadlines();
        } else {
            ui.showDeadlines(reminderList);
        }
    }
}