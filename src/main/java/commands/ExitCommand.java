package commands;

import tasks.TaskList;
import controlpanel.Ui;
import controlpanel.Storage;

/**
 * the exit command aims to trigger the greeting and cease the overall program.
 */
public class ExitCommand extends Command {

    /**
     * A default constructor.
     */
    public ExitCommand() {
    }

    /**
     * This method labels whether this command means ceasing the overall program.
     * @return Whether this command means ceasing the overall program.
     */
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * This method executes the exit command. It will send a goodbye message and cease the overall program.
     * @param tasks The task list object to interact with the checklist.
     * @param ui To print something needed in user interface.
     * @param storage To re-save the data in local disk if necessary.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.appendToOutput("     Bye. Hope to see you again soon!\n");
    }
}
