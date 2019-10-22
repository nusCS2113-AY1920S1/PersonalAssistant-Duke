package seedu.duke.common.command;

import seedu.duke.Duke;

/**
 * Exit Command is a special kind of command called when the user ends the interaction.
 */
public class ExitCommand extends Command {
    /**
     * Simply returns false when executed. s
     *
     * @return always false.
     */
    @Override
    public boolean execute() {
        responseMsg = "Bye, hope to see you again.";
        Duke.getUI().showResponse(responseMsg);
        return false;
    }
}
