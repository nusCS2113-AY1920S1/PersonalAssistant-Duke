package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.Deadline;
import duchess.model.task.Task;
import duchess.model.task.TaskList;
import duchess.ui.Ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Searches Tasklist and filters out deadline objects.
 */
public class ReminderCommand extends Command {

    /**
     * Displays Deadline objects to user.
     *
     * @param taskList List containing tasks
     * @param ui Userinterface object
     * @param storage Storage object
     * @throws DukeException Exception thrown when storage not found
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        List<Task> reminderList = addDeadlines(taskList);
        display(reminderList, ui);
    }

    /**
     * Returns a List of Task objects.
     * Adds objects of type Deadline to reminderList.
     *
     * @param taskList of user inputs
     */
    private List<Task> addDeadlines(TaskList taskList) {
        return taskList.getTasks().stream()
                .map(task -> task.getReminders())
                .flatMap(Collection::stream)
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