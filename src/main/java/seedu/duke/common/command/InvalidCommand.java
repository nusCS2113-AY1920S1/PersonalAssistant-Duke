package seedu.duke.common.command;

import seedu.duke.common.command.Command;

/**
 * InvalidCommand is a specific kind of command indicating the input is not recognised as any known command.
 */
public class InvalidCommand extends Command {

    /**
     * Simply returns false. The UI output is currently disabled.
     *
     * @return false.
     */
    @Override
    public boolean execute() {
        //if (!silent) {
        //    Duke.getUI().showError("Invalid Command Received");
        //}
        responseMsg = "This is an invalid command. Enter \'help\' for more information.";
        return false;
    }
}
