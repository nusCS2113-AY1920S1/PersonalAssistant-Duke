package dolla.parser;

import dolla.command.*;

public class EntryParser extends Parser { public EntryParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {

        if (commandToRun.equals("entries")) { //show entry list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals("add")) {
            if (verifyAddCommand() == true) {
                return new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]), inputArray[3], date);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals("sort")) {
            return new SortCommand(mode, inputArray[1]);
        } else if (commandToRun.equals("modify")) {
            if (verifyModifyCommand() == true) {
                return new InitialModifyCommand(inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else {
            return invalidCommand();
        }
    }



}
