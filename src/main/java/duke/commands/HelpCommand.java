package duke.commands;

import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Class representing a command to show the help message.
 */
public class HelpCommand extends Command {
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) {
        ui.showHelp();
    }
}
