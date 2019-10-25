package seedu.duke.common.command;

import seedu.duke.Duke;
import seedu.duke.common.model.Model;

/**
 * Exit Command is a special kind of command called when the user ends the interaction.
 */
public class ExitCommand extends Command {
    /**
     * Simply returns false when executed.
     *
     * @return always false.
     */
    @Override
    public boolean execute(Model moel) {
        responseMsg = "Bye, hope to see you again.";
        Duke.getUI().showResponse(responseMsg);
        return false;
    }
}
