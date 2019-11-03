package seedu.duke.common.command;

import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.common.model.Model;
import seedu.duke.ui.UI;

public class FlipCommand extends Command {

    @Override
    public boolean execute(Model model) {
        responseMsg = "Input type flipped.";
        CommandParseHelper.flipInputType();
        UI.getInstance().showResponse(responseMsg);
        return true;
    }
}
