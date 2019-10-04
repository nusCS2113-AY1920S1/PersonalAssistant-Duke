package seedu.hustler.command;

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
