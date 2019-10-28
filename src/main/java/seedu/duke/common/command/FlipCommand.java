package seedu.duke.common.command;

import seedu.duke.CommandParseHelper;
import seedu.duke.Duke;
import seedu.duke.common.model.Model;

public class FlipCommand extends Command {

    @Override
    public boolean execute(Model model) {
        responseMsg = "Input type flipped.";
        CommandParseHelper.flipInputType();
        Duke.getUI().showResponse(responseMsg);
        return true;
    }
}
