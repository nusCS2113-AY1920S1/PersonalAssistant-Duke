package seedu.duke.common.command;

import seedu.duke.common.model.Model;
import seedu.duke.ui.UI;

/**
 * InvalidCommand is a specific kind of command indicating the input is not recognised as any known command.
 */
public class InvalidCommand extends Command {
    String msg;

    public InvalidCommand(String msg) {
        this.msg = msg;
    }

    public InvalidCommand(){}

    /**
     * Simply returns false. The UI output is currently disabled.
     *
     * @return false.
     */
    @Override
    public boolean execute(Model model) {
        responseMsg = "This is an invalid command.";
        if (msg != null) {
            responseMsg += System.lineSeparator() + System.lineSeparator() + msg;
        }
        responseMsg += System.lineSeparator() + System.lineSeparator() + "Enter \'help\' for more "
                + "information.";
        UI.getInstance().showResponse(responseMsg);
        return false;
    }
}
