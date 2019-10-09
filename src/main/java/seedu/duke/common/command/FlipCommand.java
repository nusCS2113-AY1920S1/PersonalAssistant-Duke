package seedu.duke.common.command;

import seedu.duke.Duke;
import seedu.duke.CommandParser;

public class FlipCommand extends Command {
    private CommandParser.InputType inputType;


    public FlipCommand(CommandParser.InputType inputType) {
        this.inputType = inputType;
    }

    @Override
    public boolean execute() {
        String msg = "Input type flipped to ";
        if (inputType == CommandParser.InputType.TASK) {
            CommandParser.setInputType(CommandParser.InputType.EMAIL);
            msg += "EMAIL.";
        } else {
            CommandParser.setInputType(CommandParser.InputType.TASK);
            msg += "TASK.";
        }
        responseMsg = msg;
        Duke.getUI().showResponse(msg);
        return true;
    }
}
