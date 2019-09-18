package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents the command from user to exit application.
 * Inherits from Command class.
 * @author Zhang Yue Han
 */

public class ExitCommand extends Command {

    /**
     * Prints exit message to user and exits the application.
     * @param ui Ui object used to display information to the user
     * @param tasks TaskList object which contains the task array list holding the task info
     * @param storage Storage object which is used to write new task to file
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        ui.goodBye();
        isExit();
    }

    /**
     * Changes the exit boolean to be true.
     * @return the value of exit as true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
