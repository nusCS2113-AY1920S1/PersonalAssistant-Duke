package seedu.duke.common.command;

import seedu.duke.common.model.Model;
import seedu.duke.ui.UI;

import java.util.ArrayList;

/**
 * A command to display all help information.
 */
public class HelpCommand extends Command {

    @Override
    public boolean execute(Model model) {
        UI.getInstance().loadHelpPage();
        return true;
    }
}
