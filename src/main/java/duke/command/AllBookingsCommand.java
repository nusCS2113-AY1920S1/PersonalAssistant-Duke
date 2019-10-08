package duke.command;

import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;


/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class AllBookingsCommand extends Command {

    /**
     * Processes the list command to display all tasks in task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        System.out.println("     Here are all the current bookings in your list:");
        for (int i = 0; i < taskList.listTask().size(); i++) {
            System.out.println(taskList.listTask().get(i));
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
