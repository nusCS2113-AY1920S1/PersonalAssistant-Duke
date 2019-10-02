package seedu.duke.command;

import seedu.duke.Parser;

public class FlipCommand extends Command{
    private Parser.InputType inputType;


    public FlipCommand(Parser.InputType inputType) {
        this.inputType = inputType;
    }

    @Override
    public boolean execute() {
        if (inputType == Parser.InputType.TASK) {
            Parser.setInputType(Parser.InputType.EMAIL);
        } else {
            Parser.setInputType(Parser.InputType.TASK);
        }
        return true;
    }
}
