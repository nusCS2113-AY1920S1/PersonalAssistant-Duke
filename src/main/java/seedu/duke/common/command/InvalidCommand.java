package seedu.duke.common.command;

import seedu.duke.Duke;
import seedu.duke.common.model.Model;

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
    public boolean execute(Model model) {
        responseMsg = "This is an invalid command. Enter \'help\' for more information.";
        Duke.getUI().showResponse(responseMsg);
        return false;
    }
}
