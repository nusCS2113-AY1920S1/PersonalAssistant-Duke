package seedu.duke.common.command;

import seedu.duke.CommandParseHelper;
import seedu.duke.Duke;

public class FlipCommand extends Command {

    @Override
    public boolean execute() {
        responseMsg = "Input type flipped.";
        CommandParseHelper.filpInputType();
        Duke.getUI().showResponse(responseMsg);
        return true;
    }
}
