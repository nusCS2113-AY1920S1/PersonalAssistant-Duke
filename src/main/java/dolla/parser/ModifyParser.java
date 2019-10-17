package dolla.parser;

import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.ModifyEntryCommand;

public class ModifyParser extends Parser {
    public ModifyParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {
        String modeToModify = mode.split(" ")[1];
        if (modeToModify.equals("entry")) {
            if (verifyAddCommand() == true) {
                return new ModifyEntryCommand(inputArray[1], stringToDouble(inputArray[2]), inputArray[3], date);
            } else {
                return new ErrorCommand();
            }
        } else {
            return new ErrorCommand();
        }

    }
}
