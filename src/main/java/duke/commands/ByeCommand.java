package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * A class representing the command to terminate and exit duke.Duke.
 */



public class ByeCommand extends Command<TaskList> {

    private boolean exit = false;

    /**
     * Executes the command and terminates duke.Duke.
     *
     * @param taskList the duke.TaskList object that contains the task list
     * @param ui the Ui object that determines the displayed output of duke.Duke
     * @param storage the storage
     * @return the string to be displayed in duke.Duke
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        exit = true;
        return ui.showByeMessage();
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    public boolean isExit() {
        return exit;
    }

}
