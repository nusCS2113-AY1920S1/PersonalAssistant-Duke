package seedu.hustler.command.taskCommand;

import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;

/**
 * Represents invalid command.
 */
public class InvalidCommand extends Command {
    /**
     * Print error for InvalidCommand.
     */
    public void execute() {
        Ui ui = new Ui();
        ui.correct_command_error();
    }
}
