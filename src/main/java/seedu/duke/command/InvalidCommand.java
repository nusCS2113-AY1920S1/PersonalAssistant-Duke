package seedu.duke.command;

import seedu.duke.ui.Ui;
import seedu.duke.task.TaskList;

/**
 * Represents invalid command.
 */
public class InvalidCommand extends Command {

    /**
     * Print error for InvalidCommand.
     */
    public void execute(TaskList list) {
        Ui ui = new Ui();
        ui.correct_command_error();
    }
}
