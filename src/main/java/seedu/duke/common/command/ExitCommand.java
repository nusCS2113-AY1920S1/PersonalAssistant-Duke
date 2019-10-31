package seedu.duke.common.command;

import seedu.duke.common.model.Model;
import seedu.duke.ui.UI;

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
    public boolean execute(Model model) {
        responseMsg = "Bye, hope to see you again.";
        UI.getInstance().showResponse(responseMsg);
        UI.getInstance().exit();
        return false;
    }
}
