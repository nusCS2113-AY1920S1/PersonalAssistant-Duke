package seedu.duke.command;

import seedu.duke.Duke;
import seedu.duke.Parser;

public class FlipCommand extends Command{
    private Parser.InputType inputType;


    public FlipCommand(Parser.InputType inputType) {
        this.inputType = inputType;
    }

    @Override
    public boolean execute() {
        String msg = "Input type flipped to ";
        if (inputType == Parser.InputType.TASK) {
            Parser.setInputType(Parser.InputType.EMAIL);
            msg += "EMAIL.";
        } else {
            Parser.setInputType(Parser.InputType.TASK);
            msg += "TASK.";
        }
        responseMsg = msg;
        Duke.getUI().showResponse(msg);
        return true;
    }
}
