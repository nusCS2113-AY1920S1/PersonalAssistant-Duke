package commands;

import tasks.TaskList;
import controlpanel.Ui;
import controlpanel.Storage;

/**
 * The command aims to view the current checklist i.e. print out all the tasks.
 * in the list.
 */
public class ViewCommand extends Command {

    /**
     * A default constructor.
     */
    public ViewCommand() {
    }

    /**
     * This method labels whether this command means ceasing the overall program.
     * @return Whether this command means ceasing the overall program.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the list command. It list out all the tasks currently in the list.
     * @param tasks The task list object to interact with the checklist.
     * @param ui To print something needed in user interface.
     * @param storage To re-save the data in local disk if necessary.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        for (int i = 1; i <= tasks.lengthOfList();i++) {
            ui.appendToOutput(" " + i + "." + tasks.getTask(i - 1).toString() + "\n");
        }
    }
}