package seedu.duke.common.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;

public class FlipCommand extends Command {

    public FlipCommand() {
    }

    @Override
    public boolean execute() {
        responseMsg = "Input type flipped.";
        CommandParser.filpInputType();
        Duke.getUI().showResponse(responseMsg);
        return true;
    }
}
