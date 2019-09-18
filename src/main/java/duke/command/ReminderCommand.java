package duke.command;

import duke.dukeexception.DukeException;
import duke.task.Deadline;
import duke.task.Task;
import duke.task.TaskList;

import java.util.ArrayList;
import java.util.List;

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
        ArrayList<Task> reminderList = new ArrayList<>();
        for (Task task : taskList.getTasks()) {
            if (task instanceof Deadline) {
                reminderList.add(task);
            }
        }
        return reminderList;
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